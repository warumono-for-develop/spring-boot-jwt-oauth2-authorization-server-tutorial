package com.warumono.auth.enums;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.Sets;
import com.warumono.auth.entities.Role;
import com.warumono.auth.entities.User;
import com.warumono.auth.enums.converters.AbstractEnumeratable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * 사용자 USER("USR"), 
 * 관리자 STAFF("STF"), 
 * 최고관리자 ADMIN("ADM"), 
 * </pre>
 * 
 * @author warumono
 *
 */
@AllArgsConstructor
@Getter
public enum Authority implements AbstractEnumeratable<Authority>
{
	/**
	 * 사용자: USR
	 */
	USER("USR"), 
	
	/**
	 * 관리자: STF
	 */
	STAFF("STF"), 
	
	/**
	 * 최고관리자: ADM
	 */
	ADMIN("ADM");
	
	private String dbData;

	@Override
	public String getToDatabaseColumn(Authority attribute)
	{
		return dbData;
	}

	@Override
	public Authority getToEntityAttribute(String dbData)
	{
		return Arrays.stream(Authority.values()).filter(e -> e.getDbData().equals(dbData)).findFirst().orElseThrow(null);
	}
	
	public static final Collection<Role> roles(Authority authority)
	{
		Collection<Role> _authorities = Sets.newHashSet();
		
		switch(authority)
		{
			case ADMIN: 
				_authorities.add(Role.on(Authority.ADMIN.name()));
			case STAFF: 
				_authorities.add(Role.on(Authority.STAFF.name()));
			default: // USER
				_authorities.add(Role.on(Authority.USER.name()));
				break;
		}
		
		return _authorities;
	}
}
