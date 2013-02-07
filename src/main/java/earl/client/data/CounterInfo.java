package earl.client.data;

import java.io.Serializable;

import earl.client.data.ref.CounterId;
import earl.client.games.Position;

public abstract class CounterInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Position position = null;
	protected boolean flipped = false;
	protected CounterId ref = null;
	
	protected CounterInfo(CounterId ref) {
		this.ref = ref;
	}
	
	public CounterId ref() {
		return ref;
	}
	
	public Position getPosition() {
		return position;
	}

	protected void setPosition(Position pos) {
		this.position = pos;
	}

	public abstract String getState();

	public void flip() {
		flipped = !flipped;
	}

	public boolean isFlipped() {
		return flipped;
	}

	@Override
	public String toString() {
		return ref()+":"+getPosition().getSVGId();
	}

}
