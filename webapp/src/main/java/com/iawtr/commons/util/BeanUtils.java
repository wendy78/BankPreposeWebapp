package com.iawtr.commons.util;

import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2006-10-28
 * <p>
 * Title: 自行封装的一个BeanUtils, 提供Spring, Apache的BeanUtils 之外的一些功能.
 * </p>
 * <p/>
 * Description: 访问在当前类声明的private/protected成员变量及private/protected函数的BeanUtils. 注意,因为必须为当前类声明的变量,通过继承获得的protected变量将不能访问, 需要转型到声明该变量的类才能访问. 反射的其他功能请使用Apache Jarkarta Commons BeanUtils
 */
public class BeanUtils {
	/**
	 * 获取当前类声明的private/protected变量值
	 */
	static public Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object); 
	}
	/**
	 * 设置当前类声明的private/protected变量值
	 */
	static public void setPrivateProperty(Object object, String propertyName, Object newValue) throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}
	/**
	 * 调用当前类声明的private函数,不包括继承的
	 */
	static public Object invokePrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = null;
		if (params != null) {
			types = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				types[i] = params[i].getClass();
			}
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}
	/**
	 * 调用当前类的公共方法或继承的方法
	 * 
	 * @param object
	 * @param methodName
	 * @param params
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	static public Object invokeProtectedMethod(Object object, String methodName, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = null;
		if (params != null) {
			types = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				types[i] = params[i].getClass();
			}
		}
		Method method = object.getClass().getMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}
	/**
	 * 克隆对象， 序列化的方式
	 * 一个序列化的对象,20101125添加
	 * 
	 * @param oldObj
	 * @return
	 */
	public static Object copy(Serializable oldObj) {
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			ObjectOutputStream oOut = new ObjectOutputStream(bOut);
			oOut.writeObject(oldObj);
			oOut.flush();
			oOut.close();
			// Retrieve an input stream from the byte array and read a copy of the object back in
			ByteArrayInputStream bIn = new ByteArrayInputStream(bOut.toByteArray());
			ObjectInputStream oIn = new ObjectInputStream(bIn);
			obj = oIn.readObject();
			oIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 * 克隆对象方法2  字段值拷贝
	 * 拷贝一个对象的字段值到另一个对象相应的字段中(final字段的值除外)
	 * @param fromObj
	 * @param toObj
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void copyFields(Object fromObj,Object toObj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		Field[] fds = fromObj.getClass().getDeclaredFields();
		Field[] tds = toObj.getClass().getDeclaredFields();
		for (int i = 0; fds != null && i < fds.length; i++) {
			Field fromfield = fds[i];
			fromfield.setAccessible(true);
			for(int j=0;tds!=null&&j<tds.length;j++){
				Field tofield = tds[i];
				if(Modifier.isFinal(tofield.getModifiers())){
					continue;
				}
				if(tofield.getName().equals(fromfield.getName())){
					setPrivateProperty(toObj, fromfield.getName(), fromfield.get(fromObj));
				}
			}
		}
	}
	/**
	 * 克隆对象方法  字段值拷贝 会排除指定字段的copy
	 * 拷贝一个对象的字段值到另一个对象相应的字段中(final字段的值除外)
	 * @param fromObj
	 * @param toObj
	 * @param excludeFields 要排除的不需要拷贝的字段
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void copyFields(Object fromObj,Object toObj,List<String> excludeFields ) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		Field[] fds = fromObj.getClass().getDeclaredFields();
		Field[] tds = toObj.getClass().getDeclaredFields();
		for (int i = 0; fds != null && i < fds.length; i++) {
			Field fromfield = fds[i];
			fromfield.setAccessible(true);
			for(int j=0;tds!=null&&j<tds.length;j++){
				Field tofield = tds[i];
				if(Modifier.isFinal(tofield.getModifiers())){
					continue;
				}
				if(excludeFields.contains(tofield.getName())){
					continue;//排除字段的值 copy
				}
				if(tofield.getName().equals(fromfield.getName())){
					setPrivateProperty(toObj, fromfield.getName(), fromfield.get(fromObj));
				}
			}
		}
	}
	/**
	 * 将给定对象的字段值(String类型的)连接起来,null或trim后的空值不进行连接
	 * 
	 * @param object
	 * @param exceptPropertyName 排除再外的字段
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static StringBuffer joinObjFieldValue(Object object, List<String> exceptPropertyName) throws IllegalArgumentException, IllegalAccessException {
		Assert.notNull(object);
		StringBuffer sb = new StringBuffer();
		Field[] fds = object.getClass().getDeclaredFields();
		for (int i = 0; fds != null && i < fds.length; i++) {
			Field field = fds[i];
			if (exceptPropertyName != null && exceptPropertyName.contains(field.getName())) {
				continue;
			}
			field.setAccessible(true);
			String typeName = field.getType().getSimpleName();
			Object fieldValue = null;
			if (typeName.equals("String")) {
				fieldValue = field.get(object);
			}
			if (fieldValue != null && !fieldValue.toString().trim().equals("")) {
				sb.append(" " + fieldValue.toString().trim());
			}
		}
		return sb;
	}
	public static void main(String[] args) {
		//测试测试
	}
}
