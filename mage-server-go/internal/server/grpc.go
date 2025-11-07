package server

import (
	"context"

	"github.com/magefree/mage-server-go/internal/auth"
	"github.com/magefree/mage-server-go/internal/config"
	"github.com/magefree/mage-server-go/internal/repository"
	"github.com/magefree/mage-server-go/internal/session"
	"github.com/magefree/mage-server-go/internal/user"
	"go.uber.org/zap"
)

// NOTE: This file requires generated protobuf code to compile.
// Run 'make proto' to generate the required .pb.go files from the .proto definitions.
// The pb import would be: pb "github.com/magefree/mage-server-go/pkg/proto/mage/v1"

// mageServer implements the MageServer gRPC service
type mageServer struct {
	// pb.UnimplementedMageServerServer // Embed unimplemented server (from generated code)

	config     *config.Config
	logger     *zap.Logger
	sessionMgr session.Manager
	userMgr    user.Manager
	tokenStore *auth.TokenStore
	db         *repository.DB
}

// NewMageServer creates a new MAGE server instance
func NewMageServer(
	cfg *config.Config,
	db *repository.DB,
	sessionMgr session.Manager,
	userMgr user.Manager,
	tokenStore *auth.TokenStore,
	logger *zap.Logger,
) *mageServer {
	return &mageServer{
		config:     cfg,
		logger:     logger,
		sessionMgr: sessionMgr,
		userMgr:    userMgr,
		tokenStore: tokenStore,
		db:         db,
	}
}

// ==================== Authentication & Connection Methods ====================

// ConnectUser handles user connection
// Implements: rpc ConnectUser(ConnectUserRequest) returns (ConnectUserResponse)
func (s *mageServer) ConnectUser(ctx context.Context) error {
	// Implementation example (requires generated protobuf types):
	/*
	func (s *mageServer) ConnectUser(ctx context.Context, req *pb.ConnectUserRequest) (*pb.ConnectUserResponse, error) {
		// Extract peer info for IP address
		host := extractHostFromContext(ctx)

		// Validate credentials
		u, err := s.userMgr.Authenticate(ctx, req.UserName, req.Password)
		if err != nil {
			return &pb.ConnectUserResponse{
				Success: false,
				Error:   err.Error(),
			}, nil
		}

		// Create or restore session
		var sess *session.Session
		if req.RestoreSessionId != "" {
			sess, _ = s.sessionMgr.GetSession(req.RestoreSessionId)
		}
		if sess == nil {
			sess = s.sessionMgr.CreateSession(req.SessionId, host)
		}

		sess.SetUserID(u.Name)
		sess.UpdateActivity()

		// Register user as connected
		s.userMgr.UserConnect(ctx, u.Name, sess.ID)

		s.logger.Info("user connected",
			zap.String("user", req.UserName),
			zap.String("session", sess.ID),
		)

		return &pb.ConnectUserResponse{
			Success:   true,
			SessionId: sess.ID,
			UserId:    u.Name,
		}, nil
	}
	*/
	return nil
}

// Ping keeps session alive
// Implements: rpc Ping(PingRequest) returns (PingResponse)
func (s *mageServer) Ping(ctx context.Context) error {
	// Implementation example:
	/*
	func (s *mageServer) Ping(ctx context.Context, req *pb.PingRequest) (*pb.PingResponse, error) {
		s.sessionMgr.UpdateActivity(req.SessionId)
		return &pb.PingResponse{Success: true}, nil
	}
	*/
	return nil
}

// AuthRegister registers a new user
// Implements: rpc AuthRegister(AuthRegisterRequest) returns (AuthRegisterResponse)
func (s *mageServer) AuthRegister(ctx context.Context) error {
	// Implementation example:
	/*
	func (s *mageServer) AuthRegister(ctx context.Context, req *pb.AuthRegisterRequest) (*pb.AuthRegisterResponse, error) {
		err := s.userMgr.Register(ctx, req.UserName, req.Password, req.Email)
		if err != nil {
			return &pb.AuthRegisterResponse{
				Success: false,
				Error:   err.Error(),
			}, nil
		}

		s.logger.Info("user registered", zap.String("username", req.UserName))

		return &pb.AuthRegisterResponse{Success: true}, nil
	}
	*/
	return nil
}

// AuthSendTokenToEmail sends password reset token to email
// Implements: rpc AuthSendTokenToEmail(AuthSendTokenToEmailRequest) returns (AuthSendTokenToEmailResponse)
func (s *mageServer) AuthSendTokenToEmail(ctx context.Context) error {
	// Implementation example:
	/*
	func (s *mageServer) AuthSendTokenToEmail(ctx context.Context, req *pb.AuthSendTokenToEmailRequest) (*pb.AuthSendTokenToEmailResponse, error) {
		// Get user by email
		userRepo := repository.NewUserRepository(s.db)
		u, err := userRepo.GetByEmail(ctx, req.Email)
		if err != nil {
			return &pb.AuthSendTokenToEmailResponse{
				Success: false,
				Error:   "user not found",
			}, nil
		}

		// Generate token
		token, err := s.tokenStore.GenerateToken(req.Email)
		if err != nil {
			return &pb.AuthSendTokenToEmailResponse{
				Success: false,
				Error:   "failed to generate token",
			}, nil
		}

		// TODO: Send email with token
		// mailClient.SendPasswordResetEmail(u.Email, u.Name, token)

		s.logger.Info("password reset token sent",
			zap.String("email", req.Email),
			zap.String("username", u.Name),
		)

		return &pb.AuthSendTokenToEmailResponse{Success: true}, nil
	}
	*/
	return nil
}

// AuthResetPassword resets user password with token
// Implements: rpc AuthResetPassword(AuthResetPasswordRequest) returns (AuthResetPasswordResponse)
func (s *mageServer) AuthResetPassword(ctx context.Context) error {
	// Implementation example:
	/*
	func (s *mageServer) AuthResetPassword(ctx context.Context, req *pb.AuthResetPasswordRequest) (*pb.AuthResetPasswordResponse, error) {
		// Verify token
		if !s.tokenStore.ConsumeToken(req.Email, req.Token) {
			return &pb.AuthResetPasswordResponse{
				Success: false,
				Error:   "invalid or expired token",
			}, nil
		}

		// Get user
		userRepo := repository.NewUserRepository(s.db)
		u, err := userRepo.GetByEmail(ctx, req.Email)
		if err != nil {
			return &pb.AuthResetPasswordResponse{
				Success: false,
				Error:   "user not found",
			}, nil
		}

		// Hash new password
		passwordHash, err := auth.HashPassword(req.NewPassword)
		if err != nil {
			return &pb.AuthResetPasswordResponse{
				Success: false,
				Error:   "failed to hash password",
			}, nil
		}

		// Update password
		if err := userRepo.UpdatePassword(ctx, u.Name, passwordHash); err != nil {
			return &pb.AuthResetPasswordResponse{
				Success: false,
				Error:   "failed to update password",
			}, nil
		}

		s.logger.Info("password reset",
			zap.String("email", req.Email),
			zap.String("username", u.Name),
		)

		return &pb.AuthResetPasswordResponse{Success: true}, nil
	}
	*/
	return nil
}

// ==================== Server Info Methods ====================

// GetServerState returns server state information
// Implements: rpc GetServerState(GetServerStateRequest) returns (GetServerStateResponse)
func (s *mageServer) GetServerState(ctx context.Context) error {
	// Implementation would return active games, players, tables, etc.
	return nil
}

// ==================== Room/Lobby Methods ====================

// ServerGetMainRoomId returns the main room ID
// Implements: rpc ServerGetMainRoomId(ServerGetMainRoomIdRequest) returns (ServerGetMainRoomIdResponse)
func (s *mageServer) ServerGetMainRoomId(ctx context.Context) error {
	// Implementation would return the main lobby room ID
	return nil
}

// ==================== Table Management Methods ====================

// RoomCreateTable creates a new game table
// Implements: rpc RoomCreateTable(RoomCreateTableRequest) returns (RoomCreateTableResponse)
func (s *mageServer) RoomCreateTable(ctx context.Context) error {
	// Implementation would:
	// 1. Validate match options
	// 2. Create new table
	// 3. Add to room
	// 4. Return table ID
	return nil
}

// ==================== Game Execution Methods ====================

// GameJoin joins a game as a player
// Implements: rpc GameJoin(GameJoinRequest) returns (GameJoinResponse)
func (s *mageServer) GameJoin(ctx context.Context) error {
	// Implementation would add player to game
	return nil
}

// SendPlayerAction sends a player action (pass, concede, etc.)
// Implements: rpc SendPlayerAction(SendPlayerActionRequest) returns (SendPlayerActionResponse)
func (s *mageServer) SendPlayerAction(ctx context.Context) error {
	// Implementation would:
	// 1. Get game session
	// 2. Validate action
	// 3. Send action to game engine
	// 4. Trigger game state update
	return nil
}

// ==================== Chat Methods ====================

// ChatSendMessage sends a chat message
// Implements: rpc ChatSendMessage(ChatSendMessageRequest) returns (ChatSendMessageResponse)
func (s *mageServer) ChatSendMessage(ctx context.Context) error {
	// Implementation would:
	// 1. Validate user not muted
	// 2. Sanitize message
	// 3. Broadcast to chat room
	return nil
}

// ==================== Admin Methods ====================

// AdminLockUser locks a user account
// Implements: rpc AdminLockUser(AdminLockUserRequest) returns (AdminLockUserResponse)
func (s *mageServer) AdminLockUser(ctx context.Context) error {
	// Implementation example:
	/*
	func (s *mageServer) AdminLockUser(ctx context.Context, req *pb.AdminLockUserRequest) (*pb.AdminLockUserResponse, error) {
		duration := time.Duration(req.DurationMinutes) * time.Minute

		err := s.userMgr.LockUser(ctx, req.UserName, duration)
		if err != nil {
			return &pb.AdminLockUserResponse{
				Success: false,
				Error:   err.Error(),
			}, nil
		}

		return &pb.AdminLockUserResponse{Success: true}, nil
	}
	*/
	return nil
}

// NOTE: Additional 50+ RPC methods would be implemented here following the same pattern.
// Each method would:
// 1. Validate input
// 2. Check permissions (via interceptors)
// 3. Execute business logic
// 4. Return response
// 5. Send WebSocket callbacks if needed (via session.CallbackChan)

// Helper function to extract host from context
func extractHostFromContext(ctx context.Context) string {
	// TODO: Extract peer address from gRPC context
	return "unknown"
}
