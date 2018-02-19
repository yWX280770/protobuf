package yzh.soft;

public class IntTrans implements Translate{

	public Object trans(Object obj) {
		return Integer.parseInt((String)obj);
	}

}
