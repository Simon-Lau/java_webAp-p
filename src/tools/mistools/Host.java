// coding in UTF8
package tools.mistools;

import java.io.FileReader;

public class Host {
	public String getHost() throws Exception {
		
		String host = "";
		try {
			// TODO: 閮ㄧ讲涔嬪墠閲嶇疆姝ゅ鍦板潃
			FileReader fr = new FileReader("C:\\host.txt");
			int ch;
			while ((ch = fr.read()) != -1) {
				host = "" + (ch - 48);
				break;
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		String hostType = null;
		
		if (host.equals("0")) {
			hostType = "娴嬭瘯鏈�";
		} else if (host.equals("1")) {
			hostType = "鐢熶骇鏈�";
		} else if (host.equals("2")) {
			hostType = "澶囦唤鏈�";
		}
		return hostType;
	}
}
