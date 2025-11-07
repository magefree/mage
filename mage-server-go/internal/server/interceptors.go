package server

import (
	"context"
	"time"

	"github.com/magefree/mage-server-go/internal/session"
	"go.uber.org/zap"
	"google.golang.org/grpc"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
)

// ChainUnaryInterceptors chains multiple unary interceptors
func ChainUnaryInterceptors(interceptors ...grpc.UnaryServerInterceptor) grpc.UnaryServerInterceptor {
	return func(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (interface{}, error) {
		chain := handler
		for i := len(interceptors) - 1; i >= 0; i-- {
			chain = buildUnaryChain(interceptors[i], chain, info)
		}
		return chain(ctx, req)
	}
}

func buildUnaryChain(interceptor grpc.UnaryServerInterceptor, handler grpc.UnaryHandler, info *grpc.UnaryServerInfo) grpc.UnaryHandler {
	return func(ctx context.Context, req interface{}) (interface{}, error) {
		return interceptor(ctx, req, info, handler)
	}
}

// LoggingInterceptor logs all RPC calls
func LoggingInterceptor(logger *zap.Logger) grpc.UnaryServerInterceptor {
	return func(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (interface{}, error) {
		start := time.Now()

		// Call handler
		resp, err := handler(ctx, req)

		// Log request
		duration := time.Since(start)
		fields := []zap.Field{
			zap.String("method", info.FullMethod),
			zap.Duration("duration", duration),
		}

		if err != nil {
			fields = append(fields, zap.Error(err))
			logger.Error("RPC failed", fields...)
		} else {
			logger.Info("RPC completed", fields...)
		}

		return resp, err
	}
}

// RecoveryInterceptor recovers from panics
func RecoveryInterceptor(logger *zap.Logger) grpc.UnaryServerInterceptor {
	return func(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (resp interface{}, err error) {
		defer func() {
			if r := recover(); r != nil {
				logger.Error("panic recovered",
					zap.String("method", info.FullMethod),
					zap.Any("panic", r),
				)
				err = status.Errorf(codes.Internal, "internal server error")
			}
		}()

		return handler(ctx, req)
	}
}

// SessionValidationInterceptor validates session for protected methods
func SessionValidationInterceptor(sessionMgr session.Manager) grpc.UnaryServerInterceptor {
	// Methods that don't require session validation
	publicMethods := map[string]bool{
		"/mage.v1.MageServer/AuthRegister":          true,
		"/mage.v1.MageServer/AuthSendTokenToEmail":  true,
		"/mage.v1.MageServer/AuthResetPassword":     true,
		"/mage.v1.MageServer/ConnectUser":           true,
		"/mage.v1.MageServer/ConnectAdmin":          true,
		"/mage.v1.MageServer/GetServerState":        true,
		"/mage.v1.MageServer/ServerGetPromotionMessages": true,
	}

	return func(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (interface{}, error) {
		// Skip validation for public methods
		if publicMethods[info.FullMethod] {
			return handler(ctx, req)
		}

		// Extract session ID from request
		sessionID := extractSessionID(req)
		if sessionID == "" {
			return nil, status.Errorf(codes.Unauthenticated, "missing session ID")
		}

		// Validate session
		if !sessionMgr.ValidateSession(sessionID) {
			return nil, status.Errorf(codes.Unauthenticated, "invalid or expired session")
		}

		// Update session activity
		sessionMgr.UpdateActivity(sessionID)

		return handler(ctx, req)
	}
}

// extractSessionID extracts session ID from request
// This is a helper function that uses reflection to get SessionId field
func extractSessionID(req interface{}) string {
	// Type assertion for common request types
	type SessionIDGetter interface {
		GetSessionId() string
	}

	if getter, ok := req.(SessionIDGetter); ok {
		return getter.GetSessionId()
	}

	return ""
}

// AdminInterceptor validates admin privileges
func AdminInterceptor(sessionMgr session.Manager) grpc.UnaryServerInterceptor {
	// Admin-only methods
	adminMethods := map[string]bool{
		"/mage.v1.MageServer/AdminGetUsers":              true,
		"/mage.v1.MageServer/AdminDisconnectUser":        true,
		"/mage.v1.MageServer/AdminMuteUser":              true,
		"/mage.v1.MageServer/AdminLockUser":              true,
		"/mage.v1.MageServer/AdminActivateUser":          true,
		"/mage.v1.MageServer/AdminToggleActivateUser":    true,
		"/mage.v1.MageServer/AdminEndUserSession":        true,
		"/mage.v1.MageServer/AdminTableRemove":           true,
		"/mage.v1.MageServer/AdminSendBroadcastMessage":  true,
	}

	return func(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (interface{}, error) {
		// Skip non-admin methods
		if !adminMethods[info.FullMethod] {
			return handler(ctx, req)
		}

		// Extract session ID
		sessionID := extractSessionID(req)
		if sessionID == "" {
			return nil, status.Errorf(codes.PermissionDenied, "missing session ID")
		}

		// Get session
		sess, ok := sessionMgr.GetSession(sessionID)
		if !ok {
			return nil, status.Errorf(codes.PermissionDenied, "invalid session")
		}

		// Check admin privileges
		if !sess.IsAdminSession() {
			return nil, status.Errorf(codes.PermissionDenied, "admin privileges required")
		}

		return handler(ctx, req)
	}
}

// MetricsInterceptor records metrics for RPC calls
func MetricsInterceptor() grpc.UnaryServerInterceptor {
	return func(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (interface{}, error) {
		start := time.Now()

		resp, err := handler(ctx, req)

		duration := time.Since(start)

		// TODO: Record metrics to Prometheus
		// - RPC call counter
		// - RPC latency histogram
		// - Error rate by method
		_ = duration

		return resp, err
	}
}

// RateLimitInterceptor provides basic rate limiting
func RateLimitInterceptor(requestsPerSecond int) grpc.UnaryServerInterceptor {
	// Simple token bucket implementation would go here
	// For now, we'll just pass through
	return func(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (interface{}, error) {
		// TODO: Implement rate limiting
		return handler(ctx, req)
	}
}

// ErrorHandler converts internal errors to gRPC errors
func ErrorHandler(err error) error {
	if err == nil {
		return nil
	}

	// Map internal errors to gRPC status codes
	switch {
	case err.Error() == "user not found":
		return status.Errorf(codes.NotFound, "user not found")
	case err.Error() == "invalid credentials":
		return status.Errorf(codes.Unauthenticated, "invalid credentials")
	case err.Error() == "username already taken":
		return status.Errorf(codes.AlreadyExists, "username already taken")
	case err.Error() == "permission denied":
		return status.Errorf(codes.PermissionDenied, "permission denied")
	default:
		// Generic internal error
		return status.Errorf(codes.Internal, "internal error: %v", err)
	}
}
