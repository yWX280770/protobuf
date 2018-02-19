package yzh.soft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.AddressBook.Builder;


public class SortUtil 
{
	
	public static HashMap<String,ProtoBean> map = new HashMap<String,ProtoBean>();
	public static HashMap<String,Translate> type = new HashMap<String,Translate>(); ;
	private static Bean root;
	
	static{
		type.put("int32", new IntTrans());
		type.put("string", new StringTrans());
	}
	
	public static ArrayList<String> SortList(ArrayList<String> list)
	{
		Collections.sort(list,new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {

				/**
				 * 先判断点的个数，点少的在前，点多的在后，相同多的按照字母顺序排序
				 */
				int i = o1.split(Consts.DOU).length - o2.split(Consts.DOU).length;
				if(0 != i)
				{
					return i;
				}
				else
				{
					return o1.compareToIgnoreCase(o2);
				}
			}
			
		} );
		return list;
		
	}
	

	/**
	 * 递归
	 * @param list
	 * @param b
	 * @param bok
	 */
	public static void resver(Bean root, Builder b, AddressBook bok) {
		Stack s = new Stack();
		s.push(b);
		m1(root.getSon().get(0).getSon(),s);
	}


/*	Person john =
			  Person.newBuilder()
			    .setId(1234)
			    .setName("John Doe")
			    .setEmail("jdoe@example.com")
			    .addPhones(
			      Person.PhoneNumber.newBuilder()
			        .setNumber("555-4321")
			        .setType(Person.PhoneType.HOME))
			    .build();
			    
	 *   E peek()  查看堆栈顶部的对象，但不从堆栈中移除它。 
 E pop() 移除堆栈顶部的对象，并作为此函数的值返回该对象。 
 E push(E item)  把项压入堆栈顶部。 
	 */
	private static void m1(ArrayList<Bean> b, Stack s) {
		
		if(null == b)
		{
			System.out.println("feck");
			return;
		}

		for(Bean t : b)
		{
			if (t.getLeaf())
			{
				if(null == t.getType())
				{
					System.out.println(t.getSuffix());
					continue;
				}
				///获取设置值
				m2(t,s.peek());
			}
			else
			{
				
				//找到字符串builder
				Object obj= findbuilder(t,s.peek());
				if(null != obj)
				{
					s.push(obj);
					m1(t.getSon(),s);
					setObj(t,s,s.pop());
				}
				else
				{
					System.out.println(t.getSuffix());
				}
				
			}
			
		}
		
	}


	private static void setObj(Bean t, Stack s, Object pop) {
		try {
			Method[] m = s.peek().getClass().getMethods();
			Object o = s.peek();
			for (Method d : m) {
				if (-1 == t.getCurrentIn()) {
					if (d.getName().equals("set" + t.getClassname())
							&& (d.getParameterTypes()[0].getName().equals(pop.getClass().getName()))) {
						
						d.invoke(o, pop);
						return;
					}
				}/* else {
					if(d.getName().equals("add"+t.getClassname()) 
							&& d.getParameterCount()>1
							&& (d.getParameterTypes()[1].getName().equals(pop.getClass().getName())))
					{
//						System.out.println(d.getName()+"-"+t.getSuffix()+":"+t.getCurrentIn());
//						System.out.println(d.getName()+"=="+Arrays.toString(d.getParameters())+"-"+t.getCurrentIn()+"==="+pop+":"+pop.getClass());
						d.invoke(o,t.getCurrentIn(),pop);
						return;
					}
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 查找对应的实例，如果没有创建对象，返回builder对象
	 * @param t
	 * @param peek  入参是builder
	 * @return
	 */
	private static Object findbuilder(Bean p, Object peek) {
		
		Method[] t = peek.getClass().getMethods();
		if(-1 == p.getCurrentIn())
		{
			try
			{
				Method m = m4(p.getClassname(),t,"has","");
				if(null != m)
				{
					boolean flag = (boolean)m.invoke(peek, null);
					if(flag)
					{
						//存在此对象
						m = m4(p.getClassname(),t,"get","Builder");
						if(null != m)
						{
							Object obj = m.invoke(peek, null);
							return obj;
						}
					}
					else
					{
						//不存在此对象，创建对象
//						getPhoneOrBuilder().toBuilder
						Method m1 = m4(p.getClassname(),t,"get","");
//						System.out.println(m1.getName());
						if(null!= m1)
						{
							Object obj = m1.invoke(peek, null);
							if(null != obj)
							{
								
								m1 = m4("toBuilder",obj.getClass().getMethods(),"","");
								obj = m1.invoke(obj, null);
								return obj;
							}
						}

					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{

			try
			{
//				getPersonOrBuilder
				Method m = m4(p.getClassname(),t,"get","BuilderList");
				if(null != m)
				{
					List count = (List)m.invoke(peek, null);
					if(count.size() > p.getCurrentIn())
					{
//						System.out.println(p.getSuffix());
//						System.out.println("--->"+count.get(p.getCurrentIn()));
						//存在此对象
						return count.get(p.getCurrentIn());
						
//						return null;
					}
					else
					{
						//不存在此对象，创建对象
//						getPhoneOrBuilder().toBuilder
						Method m1 = m5("addPersonBuilder",t,"","");
						if(null!= m1)
						{
							Object obj = m1.invoke(peek, p.getCurrentIn());
							return obj;
						}

					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		}
		return null;
	}


	private static Method m4(String classname, Method[] t,String s, String string2) {
		for(Method t1 : t)
		{
			if((s+classname+string2).equals(t1.getName()))
			{
				return t1;
			}
		}
		return null;
	}
	private static Method m5(String classname, Method[] t,String s, String string2) {
		for(Method t1 : t)
		{
			if((s+classname+string2).equals(t1.getName()) && t1.getParameterCount()>0)
			{
				return t1;
			}
		}
		return null;
	}


	private static void m2(Bean t, Object obj) {
		
		Method m = findMethod(t,obj,"set");
		try {
			m.setAccessible(true);
			m.invoke(obj, type.get(t.getType().getTypeCls()).trans(t.getValue()));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private static Method findMethod(Bean t, Object obj,String s) {
		Method[] m = obj.getClass().getMethods();
		String set = s+t.getClassname();
		for(Method m1 : m)
		{
			if(set.equals(m1.getName()))
			{
				return m1;
			}
		}
		return null;
	}


	public static void read(ArrayList<String> arr){
		
		BufferedReader bis = null;
		FileInputStream fin = null;
		try
		{
			fin = new FileInputStream(new File("protoc/proto.proto"));
			bis = new BufferedReader(new InputStreamReader(fin));
			String str ;
			int count = 4;
			while(null !=(str = bis.readLine())){
				if (count-- > 0)
				{
					continue;
				}
				arr.add(str);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(null != bis)
			{
				try 
				{
					bis.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}finally
				{
					bis = null;
				}
			}
			
			if(null != fin)
			{
				try 
				{
					fin.close();
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					fin = null;
				}
			}
		}
		
	}

	public static void main(String[] args) {
		ArrayList<String> arr = new ArrayList<String>();
		SortUtil.read(arr);
		HashMap<String, ProtoBean> map = new HashMap<String, ProtoBean>();
		SortUtil.parse(arr,map);
		System.out.println(map);
		String s = "aaaa=b";
		System.out.println(s.substring(s.indexOf("=")+1));
		System.out.println(Arrays.toString("AddressBook.person[0].id=1".split("\\.")));
		
		
	}

	public static void parse(ArrayList<String> arr, HashMap<String, ProtoBean> map) {
		
		String mess = null ;
		String[] str;
		ProtoBean bean ;
		for(String s : arr)
		{
			if(s.trim().startsWith("message"))
			{
				str = s.split(Consts.BLANK);
				if(str.length>2)
				{
					mess = str[1];
					continue;
				}
			}
			else
			{
				str = s.trim().split(Consts.BLANK);
				if(str.length > 2)
				{
					bean = new ProtoBean(mess,str);
					map.put(bean.getName(),bean);
				}
				
			}
		}
		
	}


	public static Bean createLink(ArrayList<String> list) {
		
		Bean root = new Bean();
		root.setName("root");
		Bean fir = root;
		Bean sec = root;
		Bean v = null;;
		String[] tmp = null;
		StringBuilder sb = new StringBuilder();
		
		for(String s : list)
		{
			if(null == s || "".equals(s))
			{
				continue;
			}
			sb.setLength(0);
			tmp = s.split(Consts.DOU);
			
			//start:值中携带点的时候特殊处理，lg：AddressBook.person[2].email=kanjing_hao@163.com
			if(s.lastIndexOf(Consts._DOU)> s.lastIndexOf(Consts.EQU))
			{
				tmp[tmp.length-2] = tmp[tmp.length-2].concat(Consts._DOU).concat(tmp[tmp.length-1]);
				tmp[tmp.length-1] = null;
			}
			//end:值中携带点的时候特殊处理，lg：AddressBook.person[2].email=kanjing_hao@163.com
			
			for(String t : tmp)
			{
				if(null == t || "".equals(t))
				{
					continue;
				}
				sb.append(t).append(".");
				v = find(sb.toString(),root);
				if(null != v)
				{
					fir = v;
					sec = fir;
					v = null;
					continue;
				}
				
				if(-1 == t.indexOf(Consts.EQU))
				{
					fir = new Bean(t,s);
					fir.setSuffix(sb.toString());
					fir.setFather(sec);
					fir.setType(map.get(fir.getTypeName()));
					if(null == sec.getSon())
					{
						sec.setSon(new ArrayList<Bean>());
					}
					sec.getSon().add(fir);
					sec = fir;
				}
				else
				{
					
					fir = new Bean(t,s,t.substring(t.indexOf(Consts.EQU)+1));
					fir.setSuffix(sb.toString());
					fir.setFather(sec);
					fir.setType(map.get(fir.getTypeName()));
					if(null == sec.getSon())
					{
						sec.setSon(new ArrayList<Bean>());
					}
					sec.getSon().add(fir);
					sec = fir;
				}
			}
		}
		
		return root;
		
	}



	private static Bean find(String t, Bean root) {
		return m3(t, root.getSon());
	}

	private static Bean m3(String t, ArrayList<Bean> arrayList) {
		if(null == arrayList)
		{
			return null;
		}
		
		for(Bean b : arrayList)
		{
			if(t.equals(b.getSuffix()))
			{
				return b;
			}
			else
			{
				if(null != b.getSon())
				{
					Bean v = m3(t,b.getSon());
					if (null != v)
					{
						return v;
					}
				}
			}
		}
		
		return null;
	}

	public static void setMap(HashMap<String, ProtoBean> map ,HashMap<String, Translate> type)
	{
		SortUtil.map = map;
		SortUtil.type = type;
		
	}
	
	public static Object CreateProto(ArrayList<String> list)
	{
//		m1();
		
		//读取proto文件
		ArrayList<String> arr = new ArrayList<String>();
		
		SortUtil.read(arr);
		SortUtil.parse(arr,map);
//		System.out.println(map);
		
//		SortUtil.setMap(map,type);
		
		//排序
		root = SortUtil.createLink(list);
//		System.out.println(list);
		
		//new AddressBook
		AddressBook.Builder b = AddressBook.newBuilder();

		//通过递归方式构建addressbook
		SortUtil.resver(root,b,null);
		
		return b;
	}
	
}
