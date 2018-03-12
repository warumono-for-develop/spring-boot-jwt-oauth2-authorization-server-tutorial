package com.warumono.auth.enums;

import java.util.Arrays;

import com.warumono.auth.enums.converters.AbstractEnumeratable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * 티켓 TICKET("TIC"), 
 * 정상 NORMAL("NML"), 
 * 휴면 DORMANCY("DOC"), 
 * 탈퇴 WITHDRAWAL("WTH"), 
 * 차단 BLOCK("BLK"), 
 * </pre>
 * 
 * @author warumono
 *
 */
@AllArgsConstructor
@Getter
public enum Scope implements AbstractEnumeratable<Scope>
{
	/**
	 * 정상: NML
	 */
	BUY("BUY"), 
	
	/**
	 * 휴면: DOC
	 */
	SELL("SEL"), 
	
	/**
	 * 탈퇴: WTH
	 */
	WITHDRAWAL("WTH"), 
	
	/**
	 * 차단: BLK
	 */
	BLOCK("BLK");
	
	private String dbData;
	
	@Override
	public String getToDatabaseColumn(Scope attribute)
	{
		return dbData;
	}

	@Override
	public Scope getToEntityAttribute(String dbData)
	{
		return Arrays.stream(Scope.values()).filter(e -> e.getDbData().equals(dbData)).findFirst().orElseThrow(null);
	}
}
