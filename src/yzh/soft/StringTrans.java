package yzh.soft;

public class StringTrans implements Translate{

	@Override
	public Object trans(Object obj) {
		return String.valueOf(obj);
	}

}
