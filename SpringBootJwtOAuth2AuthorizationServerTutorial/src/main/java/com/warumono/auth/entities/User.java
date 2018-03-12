package com.warumono.auth.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "on")
@AllArgsConstructor(staticName = "of")
@Builder
@Setter
@Getter
@Table
(
//	name = "user", 
	uniqueConstraints = 
	{
		@UniqueConstraint(name = "UNIQ_USERNAME_IN_USER", columnNames = { "username" })
	}
)
@Entity
public class User extends AuditingEntity
{
	@NonNull
	@Column(nullable = false, updatable = false, length = 20, columnDefinition = "VARCHAR(20) COMMENT '아이디'")
	private String username;

	@NonNull
	@Column(nullable = false, updatable = false, length = 255, columnDefinition = "VARCHAR(255) COMMENT '비밀번호'")
	private String password;

	@Default
	@Column(nullable = false, updatable = true, columnDefinition = "BOOLEAN COMMENT '사용여부'")
	private Boolean enabled = Boolean.TRUE;
	
	@JsonManagedReference
	@Default
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private Collection<Role> roles = Sets.newHashSet();
	
	public void bind(Role role)
	{
		roles.add(role);
		role.setUser(this);
	}
	
	public String[] roles()
	{
		return roles.toArray(new String[roles.size()]);
	}
}
