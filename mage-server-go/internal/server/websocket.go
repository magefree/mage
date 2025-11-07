package server

import (
	"encoding/json"
	"net/http"
	"time"

	"github.com/gorilla/websocket"
	"github.com/magefree/mage-server-go/internal/config"
	"github.com/magefree/mage-server-go/internal/session"
	"go.uber.org/zap"
)

var upgrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		// TODO: Add proper origin validation
		return true
	},
	ReadBufferSize:  1024,
	WriteBufferSize: 1024,
}

// WebSocketServer handles WebSocket connections for server-to-client callbacks
type WebSocketServer struct {
	sessionMgr session.Manager
	logger     *zap.Logger
	config     config.WebSocketConfig
}

// StartWebSocketServer starts the WebSocket server
func StartWebSocketServer(cfg config.WebSocketConfig, sessionMgr session.Manager, logger *zap.Logger) error {
	ws := &WebSocketServer{
		sessionMgr: sessionMgr,
		logger:     logger,
		config:     cfg,
	}

	http.HandleFunc("/ws", ws.handleConnection)

	logger.Info("starting WebSocket server", zap.String("address", cfg.Address))

	return http.ListenAndServe(cfg.Address, nil)
}

// handleConnection handles a WebSocket connection
func (ws *WebSocketServer) handleConnection(w http.ResponseWriter, r *http.Request) {
	// Extract session ID from query params
	sessionID := r.URL.Query().Get("sessionId")
	if sessionID == "" {
		http.Error(w, "missing sessionId", http.StatusBadRequest)
		return
	}

	// Validate session
	sess, ok := ws.sessionMgr.GetSession(sessionID)
	if !ok {
		http.Error(w, "invalid session", http.StatusUnauthorized)
		return
	}

	// Upgrade connection
	conn, err := upgrader.Upgrade(w, r, nil)
	if err != nil {
		ws.logger.Error("failed to upgrade connection", zap.Error(err))
		return
	}
	defer conn.Close()

	ws.logger.Info("WebSocket connected",
		zap.String("session", sessionID),
		zap.String("user", sess.GetUserID()),
	)

	// Start ping handler in background
	done := make(chan struct{})
	go ws.pingHandler(conn, done)

	// Read callback channel and send to client
	for {
		select {
		case event, ok := <-sess.CallbackChan:
			if !ok {
				// Channel closed, connection terminated
				ws.logger.Info("callback channel closed", zap.String("session", sessionID))
				close(done)
				return
			}

			if err := ws.sendEvent(conn, event); err != nil {
				ws.logger.Error("failed to send event",
					zap.Error(err),
					zap.String("session", sessionID),
				)
				close(done)
				return
			}

		case <-done:
			return
		}
	}
}

// sendEvent sends an event to the client via WebSocket
func (ws *WebSocketServer) sendEvent(conn *websocket.Conn, event interface{}) error {
	// Convert event to JSON
	jsonData, err := json.Marshal(event)
	if err != nil {
		return err
	}

	// Set write deadline
	conn.SetWriteDeadline(time.Now().Add(10 * time.Second))

	// Send message
	return conn.WriteMessage(websocket.TextMessage, jsonData)
}

// pingHandler sends periodic ping messages to keep connection alive
func (ws *WebSocketServer) pingHandler(conn *websocket.Conn, done chan struct{}) {
	ticker := time.NewTicker(ws.config.PingInterval)
	defer ticker.Stop()

	for {
		select {
		case <-ticker.C:
			conn.SetWriteDeadline(time.Now().Add(10 * time.Second))
			if err := conn.WriteMessage(websocket.PingMessage, nil); err != nil {
				close(done)
				return
			}

		case <-done:
			return
		}
	}
}
