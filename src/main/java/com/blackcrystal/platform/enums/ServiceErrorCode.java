package com.blackcrystal.platform.enums;

public enum ServiceErrorCode implements PersistentEnum<Integer> {
	/**
	 * 未知异常
	 */
	SYSERROR(0xFFFF),

	/** 成功 */
	SUCCESS(0X0000),

	C0001(0X0001);

	private Integer code;

	private ServiceErrorCode(Integer code) {
		this.code = code;
	}

	public Integer getValue() {
		return code;
	}

	@Override
	public String toString() {
		return code.toString();
	}

	public static void main(String[] args) {
		System.out.println(C0001);
	}

}
