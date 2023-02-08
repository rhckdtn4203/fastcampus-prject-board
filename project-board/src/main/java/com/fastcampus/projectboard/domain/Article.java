package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@ToString
@Table(indexes = )
public class Article {
	private long id ;
	private String title; //제목
	private String content; // 본문
	private String hashtag; //해시태그
	
	//meta data
	private LocalDateTime createdAt; //생성일시
	private String createdBy;	//생성자
	private LocalDateTime modifiedAt; //수정일시
	private String modifiedBy;	//수정자
}
