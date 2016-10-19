package com.souvc.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class TestDom {

	/**
	 * 使用SAXReader解析XML文件
	 */
	@Test
	public void testReadXml() {
		try {
			// 创建SAXReader
			SAXReader reader = new SAXReader();
			// 读取指定文件
			Document doc = reader.read(new File("src/main/resource/EmpList.xml"));
			// 获取根节点list
			Element root = doc.getRootElement();
			// 获取list下的所有子节点emp
			@SuppressWarnings("unchecked")
			List<Element> elements = root.elements();
			// 保存所有员工对象的集合
			List<Emp> emps = new ArrayList<Emp>();
			for (Element element : elements) {
				int id = Integer.parseInt(element.attribute("id").getValue());
				String name = element.elementText("name");
				int age = Integer.parseInt(element.elementText("age"));
				String gender = element.elementText("gender");
				double salary = Double.parseDouble(element.elementText("salary"));
				Emp emp = new Emp(id, name, age, gender, salary);
				emps.add(emp);
			}
			System.out.println("解析完毕");
			System.out.println(emps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试XMLWriter写xml
	 */
	@Test
	public void testWriteXml() {
		
		List<Emp> emps = new ArrayList<Emp>();
		emps.add(new Emp(1, "张三", 33, "男", 9000));
		emps.add(new Emp(2, "李四", 26, "男", 5000));
		emps.add(new Emp(3, "王五", 48, "男", 34000));
		
		try {
			Document doc = DocumentHelper.createDocument();
			
			// 添加根标记
			Element root = doc.addElement("list");
			for (Emp emp : emps) {
				// 向根元素中添加名为emp的子元素
				Element ele = root.addElement("emp");
				// 为emp元素添加属性id
				ele.addAttribute("id", emp.getId() + "");
				ele.addElement("name").addText(emp.getName());
				ele.addElement("age").addText(emp.getAge() + "");
				ele.addElement("gender").addText(emp.getGender());
				ele.addElement("salary").addText(emp.getSalary() + "");
			}
			
			// 写出
			XMLWriter writer = new XMLWriter();
			FileOutputStream fos = new FileOutputStream("src/main/resource/emps.xml");
			writer.setOutputStream(fos);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
     * 使用XPath查找指定信息
     */
    @Test
    public void findId() {
        try {
            // 创建SAXReader
            SAXReader reader = new SAXReader();
            // 读取指定文件
            Document doc = reader.read(new File("src/main/resource/emps.xml"));
            // 查找id为1的用户信息
            List list = doc.selectNodes("/list/emp[@id='2']");
            System.out.println("选取了:" + list.size() + "条数据");
            for (Object o : list) {
                Element e = (Element) o;
                System.out.println("name:" + e.elementText("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	
	
	

}