package com.warumono.auth.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class AuditingEntity
{
//	@JsonProperty(access = Access.WRITE_ONLY)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false, columnDefinition = "BIGINT(10) UNSIGNED COMMENT '서버내의 고유 식별 키'")
	private Long seq;
	
	@Column(updatable = false, nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '클라이언트와 서버간의 고유 식별 키'")
	private String identity = UUID.randomUUID().toString();

	@JsonProperty(access = Access.READ_WRITE)
	@LastModifiedDate
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(updatable = true, nullable = true, columnDefinition = "DATETIME COMMENT '수정일시'")
	private Date updatedAt;

	@JsonProperty(access = Access.READ_WRITE)
	@CreatedDate
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(updatable = false, nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'")
	private Date registedAt;
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
