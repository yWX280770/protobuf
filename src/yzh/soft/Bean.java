package yzh.soft;

import java.util.ArrayList;

public class Bean {
	
	String name;
	
	String sourceStr;
	
	String suffix;
	
	int currentIn = -1;
	
	int totalIn;
	
	Boolean leaf = false;
	
	String value;
	
	ArrayList<Bean> son;
	
	Bean father;
	
	String classname;
	
	ProtoBean type;
	
	String typeName;

	public Bean(String s) {
		this.sourceStr = s;
		String[] tmp = s.split(Consts.DOU);
		this.totalIn = tmp.length+1;
	}

	public Bean(String name , String s) {
		this(s);
		this.name = name;
	
		String[] tmp = name.split("\\["); 
		if(tmp.length>1)
		{
			typeName = tmp[0];
			classname = tmp[0].toUpperCase().substring(0, 1).concat(tmp[0].substring(1));
			this.currentIn = Integer.parseInt(tmp[1].substring(0,tmp[1].length()-1));
		}
		else
		{
			typeName = name;
			classname = name.toUpperCase().substring(0, 1).concat(name.substring(1));
		}
		if(-1 != name.indexOf(Consts.EQU))
		{
			String[] t = name.split(Consts.EQU);
			typeName = t[0];
			classname = t[0].toUpperCase().substring(0, 1).concat(t[0].substring(1));
		}
		
	}

	public Bean(String name , String s ,String value) {
		this(name,s);
		this.value = value.trim();
		this.leaf = true;
	}
	
	public Bean() {
		// TODO Auto-generated constructor stub
	}

	public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public int getCurrentIn() {
		return currentIn;
	}

	public void setCurrentIn(int currentIn) {
		this.currentIn = currentIn;
	}

	public int getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(int totalIn) {
		this.totalIn = totalIn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Bean> getSon() {
		return son;
	}

	public void setSon(ArrayList<Bean> son) {
		this.son = son;
	}

	public Bean getFather() {
		return father;
	}

	public void setFather(Bean father) {
		this.father = father;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public ProtoBean getType() {
		return type;
	}

	public void setType(ProtoBean type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


}
