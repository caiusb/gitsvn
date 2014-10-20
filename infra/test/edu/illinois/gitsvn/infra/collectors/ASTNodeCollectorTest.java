package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ASTNodeCollectorTest extends DataCollectorTestCase {
	
	@Before
	public void before() throws IOException {
		initTest(new ASTNodeCollector("/Volumes/RAM Disk"));
	}
	
	@Test
	public void testAddClass() throws Exception {
		add("F1.java", "public class X{}", "Adding a class");
		finder.find();
		assertEquals("3; ",collector.data);
	}
	
	@Test
	public void testChangeTypeName() throws Exception {
		add("F1.java", "public class X{}");
		add("F1.java", "public class A{}");
		finder.find();
		
		assertEquals("1; 3; ", collector.data);
	}
	
	@Test
	public void testAddTwoMethods() throws Exception {
		add("F1.java", "public class F1{}");
		add("F1.java", "public class F1{void m(){} void n(){}}");
		finder.find();
		
		assertEquals("8; 3; ", collector.data);
	}
}
