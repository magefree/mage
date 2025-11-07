# MAGE Server - Go Implementation

A high-performance, scalable reimplementation of the MAGE (Magic Another Game Engine) server in Go using gRPC and WebSocket.

## Overview

This is a modern server implementation that provides the same API as the existing Java MAGE server, allowing existing clients to connect with minimal changes. The server uses:

- **gRPC** for all request/response RPC methods (60+ methods)
- **WebSocket** for server-to-client push events (game updates, callbacks)
- **PostgreSQL** for persistent data storage
- **Protocol Buffers** for efficient serialization

## Architecture

### Protocol Architecture: gRPC + WebSocket Hybrid

- **gRPC**: All request/response RPC methods from MageServer interface
- **WebSocket**: Server-to-client push events (callbacks from ClientCallback system)
- **Rationale**: gRPC provides type-safe, efficient RPC with built-in Protocol Buffers. WebSocket handles real-time push notifications.

### Technology Stack

- **Language**: Go 1.21+
- **RPC**: gRPC (google.golang.org/grpc)
- **Real-time**: WebSocket (github.com/gorilla/websocket)
- **Serialization**: Protocol Buffers
- **Database**: PostgreSQL 15+ with pgx driver
- **Configuration**: Viper (YAML + environment variables)
- **Logging**: Zap (structured, high-performance)
- **Password Hashing**: Argon2id
- **Session Storage**: In-memory (Redis-ready interface)

## Project Structure

```
mage-server-go/
├── cmd/server/              # Server entry point
├── api/proto/               # Protocol Buffer definitions
├── internal/                # Internal packages
│   ├── server/             # gRPC & WebSocket servers
│   ├── session/            # Session management
│   ├── user/               # User management
│   ├── auth/               # Authentication
│   ├── table/              # Table controller
│   ├── game/               # Game controller
│   ├── tournament/         # Tournament system
│   ├── draft/              # Draft system
│   ├── room/               # Room/lobby management
│   ├── chat/               # Chat system
│   ├── repository/         # Database layer
│   ├── rating/             # Glicko rating system
│   ├── mail/               # Email service
│   ├── plugin/             # Plugin registry
│   ├── config/             # Configuration
│   └── cache/              # Caching layer
├── pkg/proto/              # Generated protobuf code
├── migrations/             # SQL migrations
├── config/                 # Configuration files
└── test/                   # Tests

```

## Getting Started

### Prerequisites

- Go 1.21 or higher
- PostgreSQL 15 or higher
- Protocol Buffer compiler (protoc)
- golang-migrate (for database migrations)

### Installation

1. **Clone the repository:**
   ```bash
   cd mage-server-go
   ```

2. **Install Go dependencies:**
   ```bash
   make deps
   ```

3. **Install development tools:**
   ```bash
   make tools
   ```

4. **Set up PostgreSQL database:**
   ```bash
   createdb mage
   ```

5. **Run database migrations:**
   ```bash
   make migrate-up
   ```

6. **Generate Protocol Buffer code:**
   ```bash
   make proto
   ```

### Configuration

1. **Copy example configuration:**
   ```bash
   cp config/config.example.yaml config/config.yaml
   ```

2. **Edit configuration:**
   - Database connection settings
   - Server addresses (gRPC and WebSocket)
   - Authentication mode
   - Email settings (optional)
   - Logging level

3. **Environment variables** (optional):
   - `DB_PASSWORD`: Database password
   - `SMTP_HOST`, `SMTP_USER`, `SMTP_PASSWORD`: SMTP settings
   - `MAILGUN_DOMAIN`, `MAILGUN_API_KEY`: Mailgun settings

### Running the Server

**Development mode:**
```bash
make run
```

**Build and run:**
```bash
make build
./bin/mage-server -config config/config.yaml
```

**With Docker:**
```bash
make docker-run
```

## Development

### Building

```bash
make build
```

### Testing

```bash
# Run all tests
make test

# Run integration tests
make test-integration
```

### Code Quality

```bash
# Format code
make fmt

# Run linters
make lint

# Run go vet
make vet
```

### Database Migrations

```bash
# Create new migration
make migrate-create NAME=add_feature

# Run migrations up
make migrate-up

# Rollback migrations
make migrate-down
```

## API Documentation

### gRPC Service

The server implements 60+ RPC methods organized into categories:

- **Authentication** (6 methods): Register, login, password reset
- **Server Info** (3 methods): State, promotion messages, feedback
- **Room/Lobby** (5 methods): Get rooms, users, tables
- **Table Management** (10 methods): Create, join, leave, watch tables/tournaments
- **Deck Management** (2 methods): Submit, save decks
- **Game Execution** (12 methods): Join, watch, send player actions
- **Draft** (6 methods): Join, pick cards, mark cards
- **Tournament** (4 methods): Join, start, quit tournaments
- **Chat** (7 methods): Join, leave, send messages
- **Replay** (6 methods): Initialize, navigate replays
- **Admin** (9 methods): User management, table removal, broadcast

See `api/proto/mage/v1/server.proto` for complete API definition.

### WebSocket Events

Server pushes real-time events to clients via WebSocket:

- Chat messages
- Game updates (state, actions, targets)
- Tournament updates (rounds, pairings)
- Draft updates (booster, picks)
- Table events (player joins, starts)

See `api/proto/mage/v1/websocket.proto` for event definitions.

## Client Integration

### Java Client Changes

To connect the existing Java client to this Go server:

1. Replace `JBoss Remoting TransporterClient` with `gRPC MageServerClient`
2. Replace `InvokerCallbackHandler` with `WebSocket Client`
3. Update serialization (JBoss Serialization → Protocol Buffers)
4. Keep all UI, game logic, views unchanged

Example:
```java
// Before (JBoss Remoting)
TransporterClient client = new TransporterClient(...);
MageServer server = (MageServer) client.getTarget();

// After (gRPC + WebSocket)
MageServerGrpc.MageServerBlockingStub server = MageServerGrpc.newBlockingStub(channel);
WebSocketClient wsClient = new WebSocketClient(wsUri);
```

## Deployment

### Docker

```bash
# Build image
make docker-build

# Run with docker-compose
make docker-run
```

### Environment Variables

- `DB_PASSWORD`: PostgreSQL password
- `SMTP_HOST`, `SMTP_USER`, `SMTP_PASSWORD`: Email settings
- `MAILGUN_DOMAIN`, `MAILGUN_API_KEY`: Mailgun settings

### Monitoring

Prometheus metrics available at `http://localhost:9090/metrics`:

- Active sessions
- Active games
- RPC request counters
- RPC latency histograms
- Database connection pool stats
- WebSocket connection count

## Current Status

### ✅ Implemented (Phase 1 - Foundation)

- [x] Project structure and Go module
- [x] Makefile with build targets
- [x] Protocol Buffer schemas (all 60+ RPC methods)
- [x] Database schema migrations (users, cards, stats, table_records)
- [x] Configuration management with Viper
- [x] Database connection layer with pgx
- [x] Basic server entry point

### ✅ Implemented (Phase 2 - Core Infrastructure)

- [x] **Session Management**: Full session lifecycle with lease mechanism, cleanup goroutine
- [x] **Authentication Service**: Argon2id password hashing, token-based password reset
- [x] **User Management**: Registration, validation, authentication, lock/mute/activate
- [x] **User Repository**: Full CRUD operations with PostgreSQL
- [x] **User Stats Repository**: Glicko rating system fields, win/loss tracking
- [x] **gRPC Server Structure**: Interceptors for auth, logging, recovery, metrics, admin
- [x] **WebSocket Server**: Real-time callback delivery via WebSocket
- [x] **Room Management**: Main lobby and room system
- [x] **Chat System**: Chat rooms, message history, user join/leave
- [x] **Plugin Registry**: Game types, tournament types, player types registry pattern

### 🔧 Partially Implemented

- [~] **gRPC RPC Methods**: Server structure complete, requires protobuf generation (`make proto`) to compile
- [~] **Main Server**: All components wired, awaiting protobuf generation to start servers

### 📋 TODO (Phase 3+)

- [ ] Generate protobuf code (`make proto` - requires protoc installation)
- [ ] Table controller and manager
- [ ] Game controller (with game engine integration interface)
- [ ] Tournament system (pairing algorithms, round management)
- [ ] Draft system (booster generation, pick handling)
- [ ] Email service (SMTP/Mailgun)
- [ ] Glicko rating calculation implementation
- [ ] Card repository with caching
- [ ] Integration tests
- [ ] Performance testing
- [ ] Client adapter (Java)

### 🎯 Next Steps

1. **Install protoc**: Required to generate Go code from .proto files
2. **Run `make proto`**: Generate protobuf Go files
3. **Compile and test**: Server should compile and run with all core features
4. **Implement game engine integration**: Interface for game execution
5. **Testing**: Comprehensive unit and integration tests

See [GO_SERVER_IMPLEMENTATION.md](../GO_SERVER_IMPLEMENTATION.md) for the complete implementation plan.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests and linters
5. Submit a pull request

## License

Same license as the main MAGE project (MIT License).

## Links

- Main MAGE Repository: https://github.com/magefree/mage
- Implementation Plan: [GO_SERVER_IMPLEMENTATION.md](../GO_SERVER_IMPLEMENTATION.md)
- Protocol Buffers: https://protobuf.dev/
- gRPC Documentation: https://grpc.io/docs/languages/go/
