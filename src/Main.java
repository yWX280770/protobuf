


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.example.tutorial.AddressBookProtos;
import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.AddressBook.Builder;
import com.google.protobuf.InvalidProtocolBufferException;

import yzh.soft.Bean;
import yzh.soft.IntTrans;
import yzh.soft.ProtoBean;
import yzh.soft.SortUtil;
import yzh.soft.StringTrans;
import yzh.soft.Translate;

public class Main 
{
//	public static HashMap<String,ProtoBean> map = new HashMap<String,ProtoBean>();
//	public static HashMap<String,Translate> type = new HashMap<String,Translate>();
//	static Bean root ;

	public static void main(String[] args) {
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("AddressBook.person[0].name= abc");
		list.add("AddressBook.person[0].id=1");
		list.add("AddressBook.person[0].phone.number=123");
		list.add("AddressBook.person[1].name= abcd");
		list.add("AddressBook.person[1].id=22");
		list.add("AddressBook.person[1].phone.number=789");
		list.add("AddressBook.sex=girl");
		list.add("AddressBook.person[2].name=bbbabcd");
		list.add("AddressBook.person[2].id=122");
		list.add("AddressBook.person[2].phone.number=799989");
		list.add("AddressBook.person[2].email=kanjing_hao@163.com");
		

//		
//		//读取proto文件
//		ArrayList<String> arr = new ArrayList<String>();
//		SortUtil.read(arr);
//		SortUtil.parse(arr,map);
////		System.out.println(map);
//		
//		SortUtil.setMap(map,type);
//		
//		//排序
////		list = SortUtil.SortList(list);
//		root = SortUtil.createLink(list);
//		System.out.println(list);
////		System.out.println(root);
//		
//
//		
//		//new AddressBook
//		AddressBook.Builder b = AddressBook.newBuilder();
//
//		//通过递归方式构建addressbook
//		SortUtil.resver(root,b,null);
//		System.out.println(b.build());
		
		String[] str = "AddressBook.person[2].email=kanjing_hao@163.com".split("\\.");
		System.out.println("AddressBook.person[2].email=kanjing_hao@163.com".lastIndexOf("."));
		System.out.println("AddressBook.person[2].email=kanjing_hao@163.com".lastIndexOf("="));
		System.out.println(Arrays.toString(str));
		
		
//		43
//		27
//		[AddressBook, person[2], email=kanjing_hao@163, com]
		
		AddressBook.Builder b = (AddressBook.Builder)SortUtil.CreateProto(list);
		
		try {
			System.out.println(AddressBook.newBuilder().mergeFrom(b.build().toByteArray()));
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public static HashMap<String, ProtoBean> getMap() {
//		return map;
//	}
//	
//	public static HashMap<String, Translate> getType() {
//		return type;
//	}

}
