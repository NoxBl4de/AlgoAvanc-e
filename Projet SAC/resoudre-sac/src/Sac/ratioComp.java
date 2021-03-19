package Sac;

import java.util.Comparator;

public class ratioComp implements Comparator<Element> {

	@Override
	public int compare(Element o1, Element o2) {
		// TODO Auto-generated method stub
		return Float.compare(o2.getRatio(), o1.getRatio());
	}

}
