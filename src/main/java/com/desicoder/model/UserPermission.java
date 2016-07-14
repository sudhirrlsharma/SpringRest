package com.desicoder.model;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_permission", 
	uniqueConstraints = @UniqueConstraint(
		columnNames = { "permission", "username" }))
public class UserPermission{

	private Integer userPermissionId;
	private User user;
	private String permission;

	public UserPermission() {
	}

	public UserPermission(User user, String permission) {
		this.user = user;
		this.permission = permission;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_permission_id", 
		unique = true, nullable = false)
	public Integer getUserPermissionId() {
		return this.userPermissionId;
	}

	public void setUserPermissionId(Integer userPermissionId) {
		this.userPermissionId = userPermissionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "permission", nullable = false, length = 45)
	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}