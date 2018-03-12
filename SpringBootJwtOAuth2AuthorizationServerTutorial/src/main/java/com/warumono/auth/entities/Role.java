package com.warumono.auth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Builder
@Setter
@Getter
@Table
(
//	 name = "role", 
	uniqueConstraints = 
	{
		@UniqueConstraint(name = "UNIQ_NAME_IN_ROLE", columnNames = { "name" })
	}
)
@Entity
public class Role extends AuditingEntity
{
	@NonNull
	@Column(nullable = false, updatable = false, length = 20, columnDefinition = "VARCHAR(20) COMMENT '권한'")
	private String name;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, referencedColumnName = "seq", name = "user_seq", foreignKey = @ForeignKey(name = "FKEY_USER_SEQ_IN_ROLE"))
	private User user;
}
