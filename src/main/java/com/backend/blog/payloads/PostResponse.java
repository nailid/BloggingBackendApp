package com.backend.blog.payloads;

import java.util.List;

import com.backend.blog.dto.PostDTO;

public class PostResponse {
	private List<PostDTO> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean firstPage;
	private boolean lastPage;
	public PostResponse() {}
	public List<PostDTO> getContent() {
		return content;
	}
	public void setContent(List<PostDTO> content) {
		this.content = content;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public boolean isLastPage() {
		return lastPage;
	}
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}
	public boolean isFirstPage() {
		return firstPage;
	}
	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}
}
