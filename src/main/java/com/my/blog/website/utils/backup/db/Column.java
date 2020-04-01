package com.my.blog.website.utils.backup.db;

import lombok.Getter;
import lombok.Setter;

public class Column {

    @Setter
	private String catalogName;

    @Setter
	private String schemaName;

    @Setter
	private String tableName;

    @Getter
    @Setter
	private String name;

    @Getter
    @Setter
	private String label;

    @Getter
    @Setter
	private int type;

    @Setter
	private String typeName;

    @Setter
	private String columnClassName;

    @Setter
	private int displaySize;

    @Setter
	private int precision;

    @Setter
	private int scale;

	@Override
	public String toString() {
		return "Column [catalogName=" + catalogName + ", schemaName="
				+ schemaName + ", tableName=" + tableName + ", name=" + name
				+ ", label=" + label + ", type=" + type + ", typeName="
				+ typeName + ", columnClassName=" + columnClassName
				+ ", displaySize=" + displaySize + ", precision=" + precision
				+ ", scale=" + scale + "]";
	}

	
}
