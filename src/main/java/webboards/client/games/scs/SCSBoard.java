package webboards.client.games.scs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import webboards.client.data.Board;
import webboards.client.games.Hex;
import webboards.client.games.Position;
import webboards.client.utils.Browser;

public class SCSBoard extends Board implements Serializable {
	private static final long serialVersionUID = 1L;
	private SCSHex[][] hexes;
	protected SCSHex[] workaround;
	protected Map<String, SCSHex> areas = new HashMap<String, SCSHex>();
	protected Map<Hex, Hex> attacks = new HashMap<Hex, Hex>();
	protected Map<SCSCounter, Hex> barrages = new HashMap<SCSCounter, Hex>(); 

	protected SCSBoard() {
	}

	public SCSBoard(SCSHex[][] hexes) {
		this.hexes = hexes;
	}

	public SCSHex get(int x, int y) {
		return hexes[x][y];
	}

	@Override
	public SCSHex getInfo(Position ref) {
		if (ref instanceof Hex) {
			Hex xy = (Hex) ref;
			return hexes[xy.x][xy.y];
		} else {
			SCSHex hex = areas.get(ref.getSVGId());
			if (hex == null) {
				areas.put(ref.getSVGId(), hex = new SCSHex());
			}
			return hex;
		}
	}

	public void declareAttack(Hex attacking, Hex defending) {
		attacks.put(attacking, defending);
	}

	public Collection<Hex> getAttacking(Hex target) {
		Collection<Hex> result = new HashSet<Hex>();
		for (Entry<Hex, Hex> attack : attacks.entrySet()) {
			Hex attacking = attack.getKey();
			Hex defending = attack.getValue();
			if(defending.equals(target)) {
				result.add(attacking);
			}
		}
		return result;
	}

	public boolean isDeclaredAttackOn(Position pos) {
		return attacks.values().contains(pos);
	}

	public Collection<SCSHex> getAttackingInfo(Hex target) {
		Collection<Hex> attacking = getAttacking(target);
		Collection<SCSHex> info = new ArrayList<SCSHex>(attacking.size());
		for (Hex hex : attacking) {
			info.add(getInfo(hex));
		}
		return info;
	}

	public void clearAttacksOn(Hex target) {
		Browser.console(attacks.toString());
		Iterator<Entry<Hex, Hex>> iterator = attacks.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Hex, Hex> e = iterator.next();
			Hex defending = e.getValue();
			if(defending.equals(target)) {
				iterator.remove();
			}
		}
		Browser.console(attacks.toString());
	}

	public void declareBarrage(SCSCounter who, Hex target) {
		barrages.put(who, target);
	}

	public Collection<SCSCounter> getBarragesOn(Hex target) {
		Collection<SCSCounter> result = new HashSet<SCSCounter>();
		for (Entry<SCSCounter, Hex> attack : barrages.entrySet()) {
			SCSCounter attacking = attack.getKey();
			Hex defending = attack.getValue();
			if(defending.equals(target)) {
				result.add(attacking);
			}
		}
		return result;
	}

	public void clearBarrageOf(SCSCounter attacing) {
		barrages.remove(attacing);
	}
}
