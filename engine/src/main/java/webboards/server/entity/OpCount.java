package webboards.server.entity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
@SuppressWarnings("unused")
public class OpCount {
	@Id
	private Long id;	
	@Parent 
	private Key<Table> table;
	private int value;

	private OpCount(){}
	public OpCount(Table table) {
		this.table = Key.create(table);
	}

	public void incement() {
		++value;
	}

	public int count() {
		return value;
	}
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
