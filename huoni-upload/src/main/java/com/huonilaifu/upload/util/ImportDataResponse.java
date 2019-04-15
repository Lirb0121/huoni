package com.huonilaifu.upload.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据导入完成后的返回vo
 * @author lirb
 *
 */
public class ImportDataResponse {
	// 总导入条数
	private int total;
	// 成功条数
	private int success;
	// 失败条数
	private int fail;
	// 导入是否完成
	private boolean done;
	// 导入失败的条目
	private List<FailItem> failItems = null;
	
	public ImportDataResponse () {
		this.total = 0;
		this.success = 0;
		this.fail = 0;
		this.done = false;
	}
	
	public int getTotal() {
		return total;
	}

	public int getSuccess() {
		return success;
	}
	
	public int getFail() {
		return fail;
	}

	public void incrSuccess() {
		this.success++;
		this.total++;
	}

	public void incrFail() {
		this.fail++;
		this.total++;
	}

	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public List<FailItem> getFailItems() {
		return failItems;
	}

	public void pushFailItem(String tableName,int index,String reason) {
		if (failItems == null) {
			failItems = new ArrayList<FailItem>();
		}
		failItems.add(new FailItem(tableName,index,reason));
	}


	private static class FailItem {
		private String tableName;
		private int index;
		private String reason;
		public FailItem(String tableName,int index,String reason) {
			this.tableName = tableName;
			this.index = index;
			this.reason = reason;
		}
		public String getTableName() {
			return tableName;
		}
		public int getIndex() {
			return index;
		}
		public String getReason() {
			return reason;
		}
	}
	
	
}
