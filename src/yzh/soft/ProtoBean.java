package yzh.soft;

public class ProtoBean 
{
	public ProtoBean(String mess, String[] str) 
	{
		parent = mess;
		clsFlag = str[0];
		typeCls = str[1];
		name = str[2];
	}

	String  typeCls;
	
	String clsFlag;
	
	String name;
	
	String parent;

	public String getTypeCls() {
		return typeCls;
	}

	public void setTypeCls(String typeCls) {
		this.typeCls = typeCls;
	}

	public String getClsFlag() {
		return clsFlag;
	}

	public void setClsFlag(String clsFlag) {
		this.clsFlag = clsFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		return parent+":"+clsFlag+":"+typeCls+":"+name;
	}

}
