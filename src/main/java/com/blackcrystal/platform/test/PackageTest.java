package com.blackcrystal.platform.test;

public class PackageTest {
	public static void main(String[] args) {
		Package pack = PackageTest.class.getPackage();
		
		Package pp = Package.getPackage("java.lang");
		System.out.println(pp);
		
		System.out.println(pack.getImplementationVersion());
	}
}
