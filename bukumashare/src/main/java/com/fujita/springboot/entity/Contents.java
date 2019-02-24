package com.fujita.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Contents {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="contents_id")
	private long contentsId;

	@Column
	private String title;

	@Column
	private String url;

	@Column(name = "read_status")
	private String readStatus;

	@Column(name="useful_category")
	private String usefulCategory;

	@Column
	private String tag;

	@Column
	private String comment;

	public long getContentsId() {
		return contentsId;
	}

	public void setContentsId(long contentsId) {
		this.contentsId = contentsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	public String getUsefulCategory() {
		return usefulCategory;
	}

	public void setUsefulCategory(String usefulCategory) {
		this.usefulCategory = usefulCategory;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
