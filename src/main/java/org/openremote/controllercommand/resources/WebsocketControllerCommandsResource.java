package org.openremote.controllercommand.resources;

import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/ws-commands")
public class WebsocketControllerCommandsResource {

    protected final static org.slf4j.Logger log = LoggerFactory.getLogger(WebsocketControllerCommandsResource.class);

    @OnOpen
    public void open(Session session) {
        log.debug("WS open for user : " +  session.getUserPrincipal().getName());
        ControllerSessionHandler.getInstance().addSession(session);
    }

    @OnClose
    public void close(Session session) {
        log.debug("WS close for user : " + session.getUserPrincipal().getName());
        ControllerSessionHandler.getInstance().removeSession(session);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        log.debug("WS message for user : " + session.getUserPrincipal().getName());
        ControllerSessionHandler.getInstance().handleMessage(message);
    }

    @OnError
    public void error(Session session, Throwable error) {
        String username = null;
        if (session != null && session.getUserPrincipal() != null) {
            username = session.getUserPrincipal().getName();
        }
        log.error("WS Error for user : " + username, error);
        close(session);
    }

}
