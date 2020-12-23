package com.kodark.news.dto;
/**
 * Comment / Comm_Repuatation
 * @author ys
 * 2020-12-23
 */

import java.util.Date;

public class CommentDto {	
	//comment
	private int id;
	private int commentId;
	private String content;
	private Date createdAt;
	private String delFlag;
	
	//comm_repuatation
	private int userId;
	private String reputation;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getReputation() {
		return reputation;
	}
	public void setReputation(String reputation) {
		this.reputation = reputation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	@Override
	public String toString() {
		return "CommentDto [id=" + id + ", commentId=" + commentId + ", content=" + content + ", createdAt=" + createdAt
				+ ", delFlag=" + delFlag + ", userId=" + userId + ", reputation=" + reputation + "]";
	}
	
	
}