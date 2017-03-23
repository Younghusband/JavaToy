package com;

// 中信银行(CNCB)外联平台(OLP)与合作方(partner,PTNR)间的请求或回应解密并验证签名类

import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import com.lsy.baselib.crypto.algorithm.DESede;
import com.lsy.baselib.crypto.algorithm.RSA;
import com.lsy.baselib.crypto.protocol.PKCS7Signature;
import com.lsy.baselib.crypto.util.Base64;
import com.lsy.baselib.crypto.util.CryptUtil;
import com.lsy.baselib.crypto.util.FileUtil;

public class OLPdecrypt
{

	private static final byte[] SALT = new byte[8];

	/*
	 * @param msg - 去掉长度头的报文密文
	 * 
	 * @param pwd - 自己私钥的口令明文
	 * 
	 * @param keyFile - 自己的私钥文件名
	 * 
	 * @param cerFile - 签名方的公钥文件名
	 * 
	 * @param encoding - 报文编码
	 * 
	 * @param isBase64Key - 会话密钥密文是否Base64编码
	 * 
	 * @param isBase64Msg - 报文密文是否Base64编码
	 * 
	 * @param isBase64Signature - 签名数据是否Base64编码
	 * 
	 * @return 报文明文
	 */
	public static byte[] decrypt_verify(byte[] encryptedMsg, String pwd, String keyFile, String cerFile, String encoding, boolean isBase64Key,
			boolean isBase64Msg, boolean isBase64Signature) throws Exception
	{
		String startTag = "<sessionkey>";
		String endTag = "</sessionkey>";

		// 拆分会话密钥和密文
		int start = indexOf(encryptedMsg, startTag.getBytes());
		int end = indexOf(encryptedMsg, endTag.getBytes());
		int len = end - (start + startTag.length());
		byte[] sessionKey = new byte[len];
		System.arraycopy(encryptedMsg, start + startTag.length(), sessionKey, 0, len);
		len = encryptedMsg.length - (end + endTag.length());
		byte[] msg = new byte[len];
		System.arraycopy(encryptedMsg, end + endTag.length(), msg, 0, len);
		if (isBase64Msg)
		{
			msg = Base64.decode(msg);
		}

		// 读取自己的私钥
		byte[] prikey = FileUtil.read4file(keyFile);
		prikey = Base64.decode(prikey);

		// 解密会话密钥
		if (isBase64Key)
		{
			sessionKey = Base64.decode(sessionKey);
		}
		PrivateKey platPriKey = CryptUtil.decryptPrivateKey(prikey, pwd.toCharArray());
		sessionKey = RSA.decrypt(sessionKey, platPriKey.getEncoded());
		//System.out.println("session key is: " + HexUtil.binary2hex(sessionKey));

		// 解密报文
		msg = DESede.decrypt(msg, sessionKey, SALT);
		//System.out.println("message after decrypt: " + new String(msg, encoding));

		// 读取签名方的公钥
		byte[] cert = FileUtil.read4file(cerFile);
		cert = Base64.decode(cert);

		// 拆分签名数据和报文明文
		startTag = "<signature>";
		endTag = "</signature>";
		start = indexOf(msg, startTag.getBytes());
		end = indexOf(msg, endTag.getBytes());
		len = end - (start + startTag.length());
		byte[] signature = new byte[len];
		System.arraycopy(msg, start + startTag.length(), signature, 0, len);
		if (isBase64Signature)
		{
			signature = Base64.decode(signature);
		}
		len = msg.length - (end + endTag.length());
		byte[] platMsg = new byte[len];
		System.arraycopy(msg, end + endTag.length(), platMsg, 0, len);

		// 验签
		X509Certificate signerCertificate = CryptUtil.generateX509Certificate(cert);
		PublicKey pukey = signerCertificate.getPublicKey();
		if (PKCS7Signature.verifyDetachedSignature(platMsg, signature, pukey))
		{
			System.out.println("验签成功");
		}
		else
		{
			System.out.println("验签失败");
		}
		return platMsg;
	}

	/*
	 * 报文签名加密
	 * 
	 * @param platMsg - 不含长度头的报文明文
	 * 
	 * @param pwd - 自己私钥的口令明文
	 * 
	 * @param keyFile - 自己的私钥文件名
	 * 
	 * @param myCerFile - 自己的公钥文件名
	 * 
	 * @param otherCerFile - 对方的公钥文件名
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public static byte[] sign_crypt(byte[] platMsg, String pwd, String keyFile, String myCerFile, String otherCerFile, boolean isBase64Key,
			boolean isBase64Msg, boolean isBase64Signature) throws Exception
	{
		// 读取自己的私钥和公钥
		byte[] prikey = FileUtil.read4file(keyFile);
		prikey = Base64.decode(prikey);
		byte[] mycert = FileUtil.read4file(myCerFile);
		mycert = Base64.decode(mycert);

		// 报文签名
		PrivateKey signerPrivatekey = CryptUtil.decryptPrivateKey(prikey, pwd.toCharArray());
		X509Certificate signerCert = CryptUtil.generateX509Certificate(mycert);
		byte[] signedMsg = PKCS7Signature.sign(platMsg, signerPrivatekey, signerCert, null, false);
		if (isBase64Signature)
		{
			signedMsg = Base64.encode(signedMsg);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write("<signature>".getBytes());
		baos.write(signedMsg);
		baos.write("</signature>".getBytes());
		baos.write(platMsg);
		baos.flush();
		byte[] msg = baos.toByteArray();

		// 读取密文接收方的公钥
		byte[] othercert = FileUtil.read4file(otherCerFile);
		othercert = Base64.decode(othercert);

		// 生成会话密钥
		byte[] sessionKey = DESede.createKey(DESede.DESEDE_KEY_168_BIT);

		// 使用会话密钥加密报文
		byte[] encryptedMsg = DESede.encrypt(msg, sessionKey, SALT);
		if (isBase64Msg)
		{
			encryptedMsg = Base64.encode(encryptedMsg);
		}

		// 加密会话密钥
		X509Certificate signerCertificate = CryptUtil.generateX509Certificate(othercert);
		PublicKey pukey = signerCertificate.getPublicKey();
		byte[] encryptedKey = RSA.encrypt(sessionKey, pukey.getEncoded());
		if (isBase64Key)
		{
			encryptedKey = Base64.encode(encryptedKey);
		}

		baos.reset();
		baos.write("<sessionkey>".getBytes());
		baos.write(encryptedKey);
		baos.write("</sessionkey>".getBytes());
		baos.write(encryptedMsg);
		baos.flush();
		encryptedMsg = baos.toByteArray();
		return encryptedMsg;
	}

	private static int indexOf(byte[] source, byte[] target)
	{
		return indexOf(source, target, 0);
	}

	private static int indexOf(byte[] source, byte[] target, int fromIndex)
	{
		return indexOf(source, 0, source.length, target, 0, target.length, fromIndex);
	}

	private static int indexOf(byte[] source, int sourceOffset, int sourceCount, byte[] target, int targetOffset, int targetCount, int fromIndex)
	{
		if (fromIndex >= sourceCount)
		{
			return targetCount == 0 ? sourceCount : -1;
		}
		if (fromIndex < 0)
		{
			fromIndex = 0;
		}
		if (targetCount == 0)
		{
			return fromIndex;
		}

		byte first = target[targetOffset];
		int max = sourceOffset + (sourceCount - targetCount);
		for (int i = sourceOffset + fromIndex; i <= max; i++)
		{
			/* Look for first character. */
			if (source[i] != first)
			{
				while (++i <= max && source[i] != first)
					;
			}

			/* Found first character, now look at the rest of v2 */
			if (i <= max)
			{
				int j = i + 1;
				int end = j + targetCount - 1;
				for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++)
					;
				if (j == end)
				{
					/* Found whole string. */
					return i - sourceOffset;
				}
			}
		}
		return -1;
	}

}
