package webboards.server.servlet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webboards.client.games.scs.bastogne.BastogneFactory;
import webboards.client.games.scs.bastogne.BastogneSide;
import webboards.server.entity.OperationEntity;
import webboards.server.entity.Table;

import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;

public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(ManagerServlet.class.getName());

	@Override
	public void init() throws ServletException {
		ObjectifyService.register(OperationEntity.class);
		ObjectifyService.register(Table.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = req.getUserPrincipal().getName();
		String sideName = req.getParameter("side");
		if (sideName != null) {
			BastogneSide side = BastogneSide.valueOf(sideName);

			Table table = new Table();
			if (side == BastogneSide.US) {
				table.player1 = user;
			} else if (side == BastogneSide.GE) {
				table.player2 = user;
			}
			table.game = new BastogneFactory();
			table.stateTimestamp = new Date();
			Result<Key<Table>> result = ofy().save().entity(table);
			String tableId = String.valueOf(result.now().getId());
			resp.sendRedirect("/bastogne/index.jsp?table=" + tableId);
		} else if (req.getParameter("imp") != null) {
			String path = req.getParameter("imp");
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			try {
				Table table = (Table) in.readObject();
				ofy().save().entity(table);
				int count = in.readInt();
				log.info("Importing table "+table.id+": "+count+" ops.");
				for(int i=0;i<count;i++) {
					OperationEntity ope = (OperationEntity) in.readObject();
					if(ope == null) {
						break;
					}
					ofy().save().entity(ope);	
				}
				resp.sendRedirect("/bastogne/index.jsp?table=" + table.id);
			} catch (ClassNotFoundException e) {
				throw new ServletException(e);
			}finally{
				in.close();
			}
		} else if (req.getParameter("exp") != null) {
			long tid = Long.parseLong(req.getParameter("exp"));
			Table table = ofy().load().type(Table.class).id(tid).get();
			LoadType<OperationEntity> load = ofy().load().type(OperationEntity.class);
			Query<OperationEntity> query = load.filter("sessionId", String.valueOf(tid));
			List<OperationEntity> ops = query.list();
			resp.setContentType("application/octet-stream");
			ObjectOutputStream out = new ObjectOutputStream(resp.getOutputStream());
			out.writeObject(table);
			out.writeInt(ops.size());
			for (OperationEntity operationEntity : ops) {
				out.writeObject(operationEntity);				
			}
			out.close();
		} else {
			req.setAttribute("webboards.started", getStarted(user));
			req.setAttribute("webboards.waiting", getWaitingForOp(user));
			req.setAttribute("webboards.invitations", getVacant(user));
			req.getRequestDispatcher("/WEB-INF/jsp/manage.jsp").forward(req, resp);
		}
	}

	private Object getWaitingForOp(String user) {
		Query<Table> query = ofy().load().type(Table.class).limit(15);
		Query<Table> q1 = query.filter("player1 =", user).filter("player2 =", null);
		Query<Table> q2 = query.filter("player2 =", user).filter("player1 =", null);
		List<Table> results = new ArrayList<Table>();
		results.addAll(q1.list());
		results.addAll(q2.list());
		return results;
	}

	public List<Table> getStarted(String user) {
		Query<Table> query = ofy().load().type(Table.class).limit(15);
		Query<Table> q1 = query.filter("player1 =", user).filter("player2 !=", null);
		Query<Table> q2 = query.filter("player2 =", user).filter("player1 !=", null);
		List<Table> results = new ArrayList<Table>();
		results.addAll(q1.list());
		results.addAll(q2.list());
		return results;
	}

	public List<Table> getVacant(String user) {
		Query<Table> query = ofy().load().type(Table.class).limit(15);
		Query<Table> q1 = query.filter("player1 !=", user).filter("player2 =", null);
		Query<Table> q2 = query.filter("player2 !=", user).filter("player1 =", null);
		List<Table> results = new ArrayList<Table>();
		results.addAll(q1.list());
		results.addAll(q2.list());
		return results;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		if (" /_ah/channel/disconnected/".equals(requestURI)) {
			ChannelService channelService = ChannelServiceFactory.getChannelService();
			ChannelPresence presence = channelService.parsePresence(req);
			String clientId = presence.clientId();
			log.info("disconnected " + clientId);
		}
	}

	protected String getCurrentUser(HttpServletRequest req) {
		Principal principal = req.getUserPrincipal();
		if (principal == null) {
			throw new SecurityException("Not logged in.");
		}
		return principal.getName();
	}
}