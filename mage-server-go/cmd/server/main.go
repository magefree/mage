package main

import (
	"context"
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"github.com/magefree/mage-server-go/internal/config"
	"github.com/magefree/mage-server-go/internal/repository"
	"go.uber.org/zap"
	"go.uber.org/zap/zapcore"
)

var (
	configPath = flag.String("config", "config/config.yaml", "path to configuration file")
	version    = "dev" // set via ldflags during build
)

func main() {
	flag.Parse()

	// Load configuration
	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Failed to load configuration: %v\n", err)
		os.Exit(1)
	}

	// Initialize logger
	logger, err := initLogger(cfg.Logging)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Failed to initialize logger: %v\n", err)
		os.Exit(1)
	}
	defer logger.Sync()

	logger.Info("starting MAGE server",
		zap.String("version", version),
		zap.String("config", *configPath),
	)

	// Create context that listens for termination signals
	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	// Set up signal handling
	sigChan := make(chan os.Signal, 1)
	signal.Notify(sigChan, os.Interrupt, syscall.SIGTERM)

	// Initialize database
	db, err := repository.NewDB(ctx, cfg.Database, logger)
	if err != nil {
		logger.Fatal("failed to connect to database", zap.Error(err))
	}
	defer db.Close()

	// Log database stats
	stats := db.Stats()
	logger.Info("database connection pool initialized",
		zap.Int32("total_conns", stats.TotalConns()),
		zap.Int32("idle_conns", stats.IdleConns()),
	)

	// TODO: Initialize session manager
	// sessionMgr := session.NewManager(cfg.Server.LeasePeriod)

	// TODO: Initialize server components
	// - User manager
	// - Auth service
	// - Room manager
	// - Table manager
	// - Game manager
	// - Tournament manager
	// - Draft manager
	// - Chat manager

	// TODO: Start gRPC server
	// grpcServer := grpc.NewServer(...)
	// pb.RegisterMageServerServer(grpcServer, mageServer)

	// TODO: Start WebSocket server
	// go server.StartWebSocketServer(cfg.Server.WebSocket, sessionMgr, logger)

	// TODO: Start metrics server if enabled
	// if cfg.Metrics.Enabled {
	//     go startMetricsServer(cfg.Metrics, logger)
	// }

	logger.Info("MAGE server started successfully",
		zap.String("grpc_address", cfg.Server.GRPC.Address),
		zap.String("websocket_address", cfg.Server.WebSocket.Address),
	)

	// Wait for termination signal
	sig := <-sigChan
	logger.Info("received shutdown signal", zap.String("signal", sig.String()))

	// Graceful shutdown
	logger.Info("shutting down gracefully...")
	cancel()

	// TODO: Stop gRPC server gracefully
	// grpcServer.GracefulStop()

	// TODO: Close all active sessions
	// sessionMgr.CloseAll()

	logger.Info("MAGE server stopped")
}

// initLogger initializes the zap logger based on configuration
func initLogger(cfg config.LoggingConfig) (*zap.Logger, error) {
	var level zapcore.Level
	switch cfg.Level {
	case "debug":
		level = zapcore.DebugLevel
	case "info":
		level = zapcore.InfoLevel
	case "warn":
		level = zapcore.WarnLevel
	case "error":
		level = zapcore.ErrorLevel
	default:
		level = zapcore.InfoLevel
	}

	var zapCfg zap.Config
	if cfg.Format == "json" {
		zapCfg = zap.NewProductionConfig()
	} else {
		zapCfg = zap.NewDevelopmentConfig()
		zapCfg.EncoderConfig.EncodeLevel = zapcore.CapitalColorLevelEncoder
	}

	zapCfg.Level = zap.NewAtomicLevelAt(level)

	return zapCfg.Build()
}
