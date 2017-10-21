
public class User {
	
	String timeZone;
	String name;
	
	public User(String a, String b) {
		timeZone = a;
		name = b;
	}
	
	@Override
	public String toString() {
		return timeZone + "|" + name;
	}
	public static void main(String[] a) {
		System.out.print("Helleeeeeeeeo\rWorld!");
	}
}
