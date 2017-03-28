package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import common.Logger;

/**
 * 
 * @author 张耀 2009-4-3
 * 
 */
public class Excel2SQL {
	private static final Logger log = Logger.getLogger(Excel2SQL.class);
	private String col_id;
	private String path = "";

	public Excel2SQL(String path) {
		this.path = path;
	}

	public void start() {
		log.info("开始处理excel文件，路径：" + path);
		File f = new File(path);
		File[] fs = f.listFiles();
		for (File ff : fs) {
			String str = ff.getName();
			if (str.endsWith("xls")) {
				log.debug("开始处理文件：" + str + "\r\n\r\n");
				processFile(ff);

			} else {
				log.debug("文件：" + str + " 不予处理！");
				continue;
			}

		}
	}

	protected void processFile(File f) {
		try {
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook
			// 从输入流创建Workbook
			InputStream is = new FileInputStream(f);
			Workbook rwb = Workbook.getWorkbook(is);
			Sheet[] sheets = rwb.getSheets();
			String fn = f.getAbsolutePath();
			fn = fn.substring(0, fn.length() - 4);

			StringBuilder sb = new StringBuilder();
			String funcodeFile = fn + "配置.sql";
			BufferedWriter writerFc = new BufferedWriter(new FileWriter(
					new File(funcodeFile)));
			for (Sheet sheet : sheets) {
				String sheetName = sheet.getName();
				log.debug("==================工作表名称：" + sheetName
						+ "======================");

				if (sheetName.endsWith("collateInfo")) {
					genCollateInfo(sheet, sb);

				} else if (sheetName.endsWith("collateState")) {
					genCollateState(sheet, sb);
				} else if (sheetName.endsWith("collateSeq")) {
					genCollateSeq(sheet, sb);
				} else if (sheetName.endsWith("merfile")) {
					genMerfile(sheet, sb);
				} else if (sheetName.endsWith("task")) {
					genTaskInfo(sheet, sb);
				} else if (sheetName.endsWith("bean")) {
					genBeanInfo(sheet, sb);
				}

			}
			writerFc.write(sb.toString());
			writerFc.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	private String getCell(Sheet sheet, int i, int j) {
		try {
			Cell cell = sheet.getCell(i, j);
			String str = cell.getContents();
			return str.trim();
		} catch (Exception ex) {
			return "";
		}
	}

	// COL_ID
	// COL_DATE
	// COL_NAME
	// MAX_SEQ
	// MIN_SEQ
	// MOD_USER
	// MOD_TIME
	// RESERVED
	// INSERT_TIME
	//
	// private int genCollateSeq(Sheet sheet,StringBuilder sb){
	// StringBuilder ss = new StringBuilder("INSERT INTO
	// collate3.t_collate_seq(" +
	// " col_id, col_name, max_seq, min_seq,col_date values(");
	// ss.append("'"+col_id+"'");
	// for(int j=0;j<sheet.getColumns() ;j++){
	// String title = getCell(sheet,j,0);
	// String content=getCell(sheet,j,1);
	// if(j==0)col_id=content;
	// log.debug(title+":"+content);
	// if(title.equals("col_status")||title.equals("error_no"))
	// {
	// ss.append(","+content);
	// continue;
	// }
	// ss.append(",'").append(content).append("'");
	// }
	// ss.append(");");
	// sb.append(ss).append("\n");
	// return 0;
	// }

	private int genCollateState(Sheet sheet, StringBuilder sb) {
		StringBuilder ss = new StringBuilder(
				"INSERT INTO umpay.t_col_state("
						+ "  col_id, next_datetime, col_status, error_no,col_date )values(");
		ss.append("'" + col_id + "'");
		for (int j = 0; j < sheet.getColumns(); j++) {
			String title = getCell(sheet, j, 0);
			String content = getCell(sheet, j, 1);
			// if(j==0)col_id=content;
			log.debug(title + ":" + content);
			if (title.equals("col_status") || title.equals("error_no")) {
				ss.append("," + content);
				continue;
			}
			ss.append(",'").append(content).append("'");
		}
		ss.append(");");
		sb.append(ss).append("\n");
		return 0;

	}

	private int genCollateSeq(Sheet sheet, StringBuilder sb) {

		StringBuilder ss = new StringBuilder("INSERT INTO umpay.t_col_seq("
				+ "  col_id, col_date, min_seq, max_seq )values(");
		ss.append("'" + col_id + "'");
		for (int j = 0; j < sheet.getColumns(); j++) {
			String title = getCell(sheet, j, 0);
			String content = getCell(sheet, j, 1);
			// if(j==0)col_id=content;
			log.debug(title + ":" + content);
			ss.append(",'").append(content).append("'");
		}
		ss.append(");");
		sb.append(ss).append("\n");
		return 0;
	}

	private int genCollateInfo(Sheet sheet, StringBuilder sb) {
		// col_id col_name run_time date_format filename_pattern retry_sec
		// auto_run today_plus_n noti_phone pre_col
		// col_type is_flt serviceid col_kind service_user devp_user

		StringBuilder ss = new StringBuilder(
				"INSERT INTO umpay.t_col_info("
						+ " col_id, col_name, run_time,date_format,filename_pattern,retry_sec,"
						+ " auto_run, today_plus_n, "
						+ " noti_phone, pre_cols,col_type,is_flt,serviceid,col_kind ,service_user ,devp_user) values(");
		// ss.append("1");
		for (int j = 0; j < sheet.getColumns(); j++) {
			String title = getCell(sheet, j, 0);
			String content = getCell(sheet, j, 1);
			if (j == 0) {
				col_id = content;
				ss.append("'").append(content).append("'");
				continue;
			}
			log.debug(title + ":" + content);
			if (title.equals("retry_sec") || title.equals("auto_run")
					|| title.equals("today_plus_n") || title.equals("is_flt")
					|| title.equals("col_type")) {
				ss.append("," + content);
				continue;
			}
			ss.append(",'").append(content).append("'");
		}
		ss.append(");");
		sb.append(ss).append("\n");
		return 0;
	}

	private int genMerfile(Sheet sheet, StringBuilder sb) {
		StringBuilder ss = new StringBuilder(
				"INSERT INTO umpay.t_col_merfile(col_id, table_list, merid, submerid, goodsid, date_field, flt_cond,extra_cond) "
						+ " values(");
		ss.append("'" + col_id + "'");
		for (int j = 0; j < sheet.getColumns(); j++) {
			String title = getCell(sheet, j, 0);
			String content = getCell(sheet, j, 1);
			// if(j==0)col_id=content;
			log.debug(title + ":" + content);
			ss.append(",'").append(content).append("'");
		}
		ss.append(");");
		sb.append(ss).append("\n");
		return 0;
	}

	private int genTaskInfo(Sheet sheet, StringBuilder sb) {

		boolean first = true;
		int rowNum = sheet.getRows();
		for (int i = 1; i < rowNum; i++) {
			StringBuilder ss = new StringBuilder(
					"INSERT INTO umpay.t_col_tasks(is_on,col_id,task_seq, task_name,"
							+ " task_class, properties)" + " values(");
			ss.append("1");
			ss.append(",'" + col_id + "'");

			String task_seq = getCell(sheet, 0, i);
			if(task_seq==null||task_seq.trim().equals("")){
				continue;
			}
			ss.append(",").append(task_seq);

			String task_name = getCell(sheet, 1, i);
			ss.append(",'").append(task_name).append("'");

			String task_class = getCell(sheet, 2, i);
			ss.append(",'").append(task_class).append("'");

			StringBuilder s1 = new StringBuilder("[");
			int columnNum = sheet.getColumns();
			for (int j = 3; j < columnNum; j++) {
				String title = getCell(sheet, j, 0);
				String content = getCell(sheet, j, i);
				log.debug(title + ":" + content);
				if (title.endsWith("bean")) {

					if (content.trim().equals(""))
						continue;
					if (first) {
						first = false;
					} else {
						s1.append(",");
					}
					title = title.substring(0, title.length() - 5);
					s1.append("{\"bean\":\"" + content + "\"," + "\"name\":\""
							+ title + "\"}");
					// continue;
				} else if (title.endsWith("value")) {
					if (content.trim().equals(""))
						continue;
					if (first) {
						first = false;
					} else {
						s1.append(",");
					}
					title = title.substring(0, title.length() - 6);

					s1.append("{\"value\":\"" + content + "\"," + "\"name\":\""
							+ title + "\"}");
					// continue;
				}else if(title.endsWith("class")){
					if (content.trim().equals(""))
						continue;
					if (first) {
						first = false;
					} else {
						s1.append(",");
					}
					title = title.substring(0, title.length() - 6);

					s1.append("{\"class\":\"" + content + "\"," + "\"name\":\""
							+ title + "\"}");
				}

				// ss.append(",'").append(content).append("'");
			}

			s1.append("]");

			ss.append(",'").append(s1.toString()).append("'");
			ss.append(");");
			sb.append(ss).append("\n");
			first=true;
		}

		return 0;
	}

	private int genBeanInfo(Sheet sheet, StringBuilder sb) {
		boolean first = true;
		int rowNum = sheet.getRows();
		for (int i = 1; i < rowNum; i++) {
			StringBuilder ss = new StringBuilder(
					"INSERT INTO umpay.t_col_beans(is_on,bean_id, bean_class,"
							+ " properties)" + " values(");
			ss.append("1");
			String bean_id = getCell(sheet, 0, i);
			if(bean_id==null||bean_id.trim().equals("")){
				continue;
			}
			ss.append(",'").append(bean_id);

			String bean_class = getCell(sheet, 1, i);
			ss.append("','").append(bean_class).append("'");

			StringBuilder s1 = new StringBuilder("[");
			int columnNum = sheet.getColumns();
			for (int j = 2; j < columnNum; j++) {
				String title = getCell(sheet, j, 0);
				String content = getCell(sheet, j, i);
				log.debug(title + ":" + content);
				if (title.endsWith("bean")) {
					if (content.trim().equals(""))
						continue;
					if (first) {
						first = false;
					} else {
						s1.append(",");
					}
					title = title.substring(0, title.length() - 5);
					s1.append("{\"bean\":\"" + content + "\"," + "\"name\":\""
							+ title + "\"}");
					// continue;
				} else if (title.endsWith("value")) {
					if (content.trim().equals(""))
						continue;
					if (first) {
						first = false;
					} else {
						s1.append(",");
					}
					title = title.substring(0, title.length() - 6);
					s1.append("{\"value\":\"" + content + "\"," + "\"name\":\""
							+ title + "\"}");
					// continue;
				}else if(title.endsWith("class")){
					if (content.trim().equals(""))
						continue;
					if (first) {
						first = false;
					} else {
						s1.append(",");
					}
					title = title.substring(0, title.length() - 6);

					s1.append("{\"class\":\"" + content + "\"," + "\"name\":\""
							+ title + "\"}");
				}


			}
			s1.append("]");
			ss.append(",'").append(s1.toString()).append("'");
			ss.append(");");
			sb.append(ss).append("\n");
			
			first=true;
		}
		return 0;
	}

	public static void main(String[] args) {
		String path = System.getProperty("user.dir");
		if (args.length > 0) {
			path = args[0];
		} else {
			path = "D:/collate3";
		}
		Excel2SQL pe = new Excel2SQL(path);
		pe.start();

	}

}
