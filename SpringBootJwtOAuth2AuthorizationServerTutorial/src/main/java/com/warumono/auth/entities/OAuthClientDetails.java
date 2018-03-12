package com.warumono.auth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "on")
@AllArgsConstructor(staticName = "of")
@Setter
@Getter
@Builder
@Table
(
	name = "oauth_client_details", 
	uniqueConstraints = 
	{
		@UniqueConstraint(name = "UNIQ_CLIENT_ID_IN_OAUTH2_CLIENT_DETAILS", columnNames = { "client_id" })
	}
)
@Entity
public class OAuthClientDetails extends AuditingEntity
{
	@NonNull
	@Column(nullable = false, updatable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '아이디'")
	private String client_id;
	
	@Column(nullable = true, updatable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '제원 아이디'")
	private String resource_ids;
	
	@NonNull
	@Column(nullable = false, updatable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '비밀번호'")
	private String client_secret;
	
	@NonNull
	@Column(nullable = false, updatable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '기능 권한'")
	private String scope;
	
	@NonNull
	@Column(nullable = false, updatable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '인증 종류'")
	private String authorized_grant_types;
	
	@Column(nullable = true, updatable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '인증 코드 전달 URI'")
	private String web_server_redirect_uri;

	@NonNull
	@Column(nullable = false, updatable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '권한'")
	private String authorities;

	@NonNull
	@Column(nullable = false, updatable = true, columnDefinition = "INT COMMENT 'Access Token 유효 시간'")
	private Long access_token_validity;
	
	@Column(nullable = true, updatable = true, columnDefinition = "INT COMMENT 'Refresh Token 유효 시간'")
	private Long refresh_token_validity;

	@Lob
	@Column(nullable = true, updatable = true, length = 4096, columnDefinition = "TEXT COMMENT '추가 정보'")
	private String additional_information;
	
	@Column(nullable = true, updatable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '자동 승인 처리 기능 권한'")
	private String autoapprove;
}
