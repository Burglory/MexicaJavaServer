package mexica.network;

public abstract class Lounge extends Room {

	public static final Lounge getDefault() {
		return new _Lounge();
	}
	
}
