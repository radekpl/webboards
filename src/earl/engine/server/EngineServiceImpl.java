package earl.engine.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import earl.engine.client.EngineService;

public class EngineServiceImpl extends RemoteServiceServlet implements
		EngineService {
	private static final long serialVersionUID = 1L;
	
	protected String getCurrentUser() {
		Principal principal = getThreadLocalRequest().getUserPrincipal();
		if(principal == null) {
			throw new SecurityException("Not logged in.");
		}
		return principal.getName();
	}

	@Override
	public void updateLocation(String unitId, String hexId)  {
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Key tableKey = getTableKey();
			Entity unit = new Entity("unit", unitId, tableKey);
			unit.setProperty("position", hexId);
			ds.put(unit);
		}catch (Exception e) {
	        throw new AssertionError(e);
		}
	}

	protected Key getTableKey() {
		String tableId = getTableId();
		Key tableKey = KeyFactory.createKey("table", tableId);
		return tableKey;
	}

	protected Entity getTable(DatastoreService ds) throws EntityNotFoundException {
		String tableId = getTableId();
		Entity table = ds.get(KeyFactory.createKey("table", tableId));
		return table;
	}

	protected String getTableId() {
		String referer = getThreadLocalRequest().getHeader("referer");
		Map<String, List<String>> queryParams = getQueryParams(referer);
		String tableId = queryParams.get("table").get(0);
		return tableId;
	}
	public static Map<String, List<String>> getQueryParams(String url) {
	    try {
	        Map<String, List<String>> params = new HashMap<String, List<String>>();
	        String[] urlParts = url.split("\\?");
	        if (urlParts.length > 1) {
	            String query = urlParts[1];
	            for (String param : query.split("&")) {
	                String[] pair = param.split("=");
	                String key = URLDecoder.decode(pair[0], "UTF-8");
	                String value = "";
	                if (pair.length > 1) {
	                    value = URLDecoder.decode(pair[1], "UTF-8");
	                }

	                List<String> values = params.get(key);
	                if (values == null) {
	                    values = new ArrayList<String>();
	                    params.put(key, values);
	                }
	                values.add(value);
	            }
	        }

	        return params;
	    } catch (UnsupportedEncodingException ex) {
	        throw new AssertionError(ex);
	    }
	}
	@Override
	public Map<String, String> getUnits() {
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Query q = new Query("unit").setAncestor(getTableKey());
			Iterable<Entity> results = ds.prepare(q).asIterable();
			Map<String, String> map = new HashMap<String, String>();
			for (Entity entity : results) {
				String unitId = entity.getKey().getName();
				String hexId = (String) entity.getProperty("position");
				map.put(unitId, hexId);
			}
			return map;
		}catch (Exception e) {
			throw new AssertionError(e);
		}
		
	}
}
