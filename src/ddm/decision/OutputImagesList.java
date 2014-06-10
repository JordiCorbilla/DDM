package ddm.decision;

import java.util.ArrayList;

public class OutputImagesList {
	private ArrayList<String> listRange = null;

	public OutputImagesList() {
		listRange = new ArrayList<String>();
	}

	private boolean exists(String description) {
		boolean found = false;
		int i = 0;
		while (!found && i < listRange.size()) {
			found = description.equals(listRange.get(i));
			i++;
		}
		return found;
	}

	public void AddItem(String image, double value) {
		String description = "Image: " + image + " associated value: " + value
				+ "\r\n";
		if (!exists(description))
			listRange.add(description);
	}

	public String ToString() {
		String s = "";
		for (int i = 0; i < listRange.size(); i++) {
			s = s + listRange.get(i);
		}
		return s;
	}
}
