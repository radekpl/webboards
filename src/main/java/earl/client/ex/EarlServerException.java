package earl.client.ex;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EarlServerException extends RuntimeException implements IsSerializable {

	private static final long serialVersionUID = 1L;

	public EarlServerException(String string) {
		super(string);
	}

	public EarlServerException(Exception e) {
		super(e);
	}

}
