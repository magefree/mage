# MAGE Server Go Implementation - Definitive Plan

## Overview
This document outlines the implementation of a new MAGE (Magic Another Game Engine) server in Go using **gRPC for RPC calls** and **WebSocket for server push events**. The server will provide the same API as the existing Java server, allowing existing clients to connect with minimal changes.

## Architecture Decision Summary

### Protocol Architecture: gRPC + WebSocket Hybrid
- **gRPC**: All request/response RPC methods (60+ methods from MageServer interface)
- **WebSocket**: Server-to-client push events (callbacks from ClientCallback system)
- **Rationale**: gRPC provides type-safe, efficient RPC with built-in Protocol Buffers. WebSocket handles real-time push notifications. This combination requires minimal client changes (swap JBoss Remoting client for gRPC + WebSocket client).

### Database: PostgreSQL
- **Choice**: PostgreSQL 15+ with pgx driver (pure Go)
- **Rationale**: Best performance, scalability, and tooling. Full-text search built-in. Production-ready.

### Password Hashing: Argon2id
- **Choice**: Argon2id (modern, secure, Go-native)
- **Rationale**: Industry-standard secure password hashing

### Session Storage: In-Memory (with Redis-ready interface)
- **Initial**: In-memory map with sync.RWMutex
- **Production**: Redis for distributed deployment (interface-based design allows easy swap)
- **Rationale**: Simplicity for initial development, scalability path for production

### Plugin System: Pre-compiled Registry Pattern
- **Choice**: All game types compiled into binary, registry-based instantiation
- **Rationale**: Go doesn't support cross-platform dynamic loading. Pre-compiled approach is reliable, performant, and simplifies deployment.
- **Extensibility**: New game types added via PR and recompilation (matches OSS workflow)

### Configuration: Viper with YAML
- **Choice**: Viper for YAML config with environment variable overrides
- **Rationale**: Industry standard, supports complex config structures

### Logging: Zap
- **Choice**: Uber's Zap (structured, high-performance)
- **Rationale**: Best-in-class performance, structured logging for debugging complex game states

### Email: Dual Provider Support
- **SMTP**: gomail.v2 for direct SMTP
- **Mailgun**: mailgun-go SDK
- **Configuration-driven selection**

### Cache Layer: In-memory with groupcache
- **Card data**: groupcache for distributed caching
- **Rationale**: Cards are read-heavy, immutable data perfect for caching

## Client Integration Strategy

### Minimal Client Changes Required

**Java Client Changes (Single PR)**:
1. Replace `JBoss Remoting TransporterClient` with `gRPC MageServerClient`
2. Replace `InvokerCallbackHandler` with `WebSocket Client`
3. Update serialization (JBoss Serialization → Protocol Buffers)
4. Keep all UI, game logic, views unchanged

**Implementation**:
```java
// Before (JBoss Remoting)
TransporterClient client = new TransporterClient(...);
client.setSessionId(sessionId);
MageServer server = (MageServer) client.getTarget();
server.connectUser(userName, password, sessionId, ...);

// After (gRPC + WebSocket)
MageServerGrpc.MageServerBlockingStub server = MageServerGrpc.newBlockingStub(channel);
ConnectUserResponse response = server.connectUser(
    ConnectUserRequest.newBuilder()
        .setUserName(userName)
        .setPassword(password)
        .setSessionId(sessionId)
        .build()
);

WebSocketClient wsClient = new WebSocketClient(wsUri);
wsClient.addMessageHandler(this::handleServerCallback);
```

**Compatibility Shim**: Create a Java adapter class that wraps gRPC/WebSocket to match the original `MageServer` interface signature, minimizing code changes in client.

## Project Structure

```
mage-server-go/
├── cmd/
│   └── server/
│       └── main.go                      # Server entry point
│
├── api/
│   └── proto/
│       └── mage/
│           └── v1/
│               ├── server.proto         # Main RPC service definition
│               ├── auth.proto           # Authentication messages
│               ├── room.proto           # Room/lobby messages
│               ├── table.proto          # Table management messages
│               ├── game.proto           # Game execution messages
│               ├── tournament.proto     # Tournament messages
│               ├── draft.proto          # Draft messages
│               ├── chat.proto           # Chat messages
│               ├── admin.proto          # Admin messages
│               ├── models.proto         # Shared data models (TableView, GameView, etc.)
│               └── websocket.proto      # WebSocket callback messages
│
├── internal/
│   ├── server/
│   │   ├── grpc.go                      # gRPC server implementation
│   │   ├── websocket.go                 # WebSocket server for callbacks
│   │   ├── interceptors.go              # gRPC interceptors (auth, logging, metrics)
│   │   └── middleware.go                # Session validation middleware
│   │
│   ├── session/
│   │   ├── session.go                   # Session struct and methods
│   │   ├── manager.go                   # SessionManager interface + impl
│   │   ├── store.go                     # Session storage (in-memory or Redis)
│   │   └── lease.go                     # Lease/ping mechanism
│   │
│   ├── user/
│   │   ├── user.go                      # User domain model
│   │   ├── manager.go                   # UserManager interface + impl
│   │   ├── repository.go                # User database operations
│   │   └── validator.go                 # Username/password validation
│   │
│   ├── auth/
│   │   ├── password.go                  # Password hashing (Argon2id)
│   │   ├── token.go                     # Password reset token generation
│   │   └── service.go                   # Auth service (registration, reset)
│   │
│   ├── table/
│   │   ├── controller.go                # TableController
│   │   ├── manager.go                   # TableManager
│   │   ├── state.go                     # Table state machine
│   │   └── seat.go                      # Player seat management
│   │
│   ├── game/
│   │   ├── controller.go                # GameController
│   │   ├── manager.go                   # GameManager
│   │   ├── player_session.go            # GameSessionPlayer
│   │   ├── watcher_session.go           # GameSessionWatcher
│   │   ├── worker.go                    # Game execution worker pool
│   │   ├── replay.go                    # Replay system
│   │   └── view.go                      # GameView generation
│   │
│   ├── tournament/
│   │   ├── controller.go                # TournamentController
│   │   ├── manager.go                   # TournamentManager
│   │   ├── session.go                   # TournamentSession
│   │   ├── pairing.go                   # Swiss/elimination pairing
│   │   └── types.go                     # Tournament type registry
│   │
│   ├── draft/
│   │   ├── controller.go                # DraftController
│   │   ├── manager.go                   # DraftManager
│   │   ├── session.go                   # DraftSession
│   │   ├── cube.go                      # Cube draft logic
│   │   └── booster.go                   # Booster pack generation
│   │
│   ├── room/
│   │   ├── room.go                      # Room interface + base impl
│   │   ├── games_room.go                # GamesRoomImpl (main lobby)
│   │   └── manager.go                   # GamesRoomManager
│   │
│   ├── chat/
│   │   ├── chat.go                      # ChatSession
│   │   ├── manager.go                   # ChatManager
│   │   ├── message.go                   # ChatMessage handling
│   │   └── sanitizer.go                 # HTML sanitization
│   │
│   ├── repository/
│   │   ├── db.go                        # Database connection management
│   │   ├── cards.go                     # CardRepository
│   │   ├── users.go                     # AuthorizedUserRepository
│   │   ├── stats.go                     # UserStatsRepository
│   │   └── records.go                   # TableRecordRepository
│   │
│   ├── rating/
│   │   ├── glicko.go                    # Glicko rating implementation
│   │   └── calculator.go                # Rating calculation
│   │
│   ├── mail/
│   │   ├── client.go                    # MailClient interface
│   │   ├── smtp.go                      # SMTP implementation
│   │   ├── mailgun.go                   # Mailgun implementation
│   │   └── templates.go                 # Email templates
│   │
│   ├── plugin/
│   │   ├── registry.go                  # Plugin registry pattern
│   │   ├── game_types.go                # Game type definitions
│   │   ├── tournament_types.go          # Tournament type definitions
│   │   └── player_types.go              # Player type definitions
│   │
│   ├── config/
│   │   ├── config.go                    # Config structs
│   │   ├── loader.go                    # Viper-based config loading
│   │   └── validator.go                 # Config validation
│   │
│   ├── cache/
│   │   └── cards.go                     # Card cache implementation
│   │
│   └── util/
│       ├── compress.go                  # Compression utilities (for Protocol Buffers)
│       ├── uuid.go                      # UUID handling
│       └── errors.go                    # Error types
│
├── pkg/
│   ├── proto/                           # Generated Go protobuf code
│   │   └── mage/
│   │       └── v1/
│   │           ├── server.pb.go
│   │           ├── server_grpc.pb.go
│   │           └── ...
│   │
│   └── models/                          # Shared models (if needed outside internal/)
│       └── version.go                   # Version handling
│
├── migrations/                          # SQL migration files
│   ├── 001_create_users_table.up.sql
│   ├── 001_create_users_table.down.sql
│   ├── 002_create_cards_table.up.sql
│   ├── 002_create_cards_table.down.sql
│   ├── 003_create_stats_table.up.sql
│   ├── 003_create_stats_table.down.sql
│   ├── 004_create_table_records.up.sql
│   └── 004_create_table_records.down.sql
│
├── config/
│   ├── config.yaml                      # Default server config
│   ├── config.example.yaml              # Example config with comments
│   └── config.dev.yaml                  # Development overrides
│
├── scripts/
│   └── generate_proto.sh                # Regenerate protobuf code
│
├── test/
│   ├── integration/                     # Integration tests
│   │   ├── session_test.go
│   │   ├── game_flow_test.go
│   │   └── tournament_test.go
│   └── testdata/                        # Test fixtures
│       └── cards.json
│
├── .proto                               # Protobuf generation config
├── Dockerfile                           # Multi-stage Docker build
├── docker-compose.yml                   # Local dev environment (server + postgres)
├── go.mod
├── go.sum
├── Makefile                             # Build targets
└── README.md                            # Go server documentation
```

## Phase-by-Phase Implementation

### Phase 1: Foundation & Protobuf Definitions (Week 1-3)

#### 1.1 Project Initialization
- [ ] Initialize Go module: `go mod init github.com/magefree/mage-server-go`
- [ ] Set up Makefile with common targets (build, test, proto, run)
- [ ] Configure protobuf tooling (buf or protoc)
- [ ] Set up CI/CD pipeline (GitHub Actions)

#### 1.2 Protocol Buffer Definitions
**Map all 60+ RPC methods to gRPC services**

**api/proto/mage/v1/server.proto**:
```protobuf
syntax = "proto3";

package mage.v1;

option go_package = "github.com/magefree/mage-server-go/pkg/proto/mage/v1";

import "mage/v1/auth.proto";
import "mage/v1/room.proto";
import "mage/v1/table.proto";
import "mage/v1/game.proto";
import "mage/v1/tournament.proto";
import "mage/v1/draft.proto";
import "mage/v1/chat.proto";
import "mage/v1/admin.proto";
import "mage/v1/models.proto";

service MageServer {
  // Authentication & Connection (6 methods)
  rpc AuthRegister(AuthRegisterRequest) returns (AuthRegisterResponse);
  rpc AuthSendTokenToEmail(AuthSendTokenToEmailRequest) returns (AuthSendTokenToEmailResponse);
  rpc AuthResetPassword(AuthResetPasswordRequest) returns (AuthResetPasswordResponse);
  rpc ConnectUser(ConnectUserRequest) returns (ConnectUserResponse);
  rpc ConnectAdmin(ConnectAdminRequest) returns (ConnectAdminResponse);
  rpc ConnectSetUserData(ConnectSetUserDataRequest) returns (ConnectSetUserDataResponse);
  rpc Ping(PingRequest) returns (PingResponse);

  // Server Info (3 methods)
  rpc GetServerState(GetServerStateRequest) returns (GetServerStateResponse);
  rpc ServerGetPromotionMessages(ServerGetPromotionMessagesRequest) returns (ServerGetPromotionMessagesResponse);
  rpc ServerAddFeedbackMessage(ServerAddFeedbackMessageRequest) returns (ServerAddFeedbackMessageResponse);

  // Room/Lobby (5 methods)
  rpc ServerGetMainRoomId(ServerGetMainRoomIdRequest) returns (ServerGetMainRoomIdResponse);
  rpc RoomGetUsers(RoomGetUsersRequest) returns (RoomGetUsersResponse);
  rpc RoomGetFinishedMatches(RoomGetFinishedMatchesRequest) returns (RoomGetFinishedMatchesResponse);
  rpc RoomGetAllTables(RoomGetAllTablesRequest) returns (RoomGetAllTablesResponse);
  rpc RoomGetTableById(RoomGetTableByIdRequest) returns (RoomGetTableByIdResponse);

  // Table Management (10 methods)
  rpc RoomCreateTable(RoomCreateTableRequest) returns (RoomCreateTableResponse);
  rpc RoomCreateTournament(RoomCreateTournamentRequest) returns (RoomCreateTournamentResponse);
  rpc RoomJoinTable(RoomJoinTableRequest) returns (RoomJoinTableResponse);
  rpc RoomJoinTournament(RoomJoinTournamentRequest) returns (RoomJoinTournamentResponse);
  rpc RoomLeaveTableOrTournament(RoomLeaveTableOrTournamentRequest) returns (RoomLeaveTableOrTournamentResponse);
  rpc RoomWatchTable(RoomWatchTableRequest) returns (RoomWatchTableResponse);
  rpc RoomWatchTournament(RoomWatchTournamentRequest) returns (RoomWatchTournamentResponse);
  rpc TableSwapSeats(TableSwapSeatsRequest) returns (TableSwapSeatsResponse);
  rpc TableRemove(TableRemoveRequest) returns (TableRemoveResponse);
  rpc TableIsOwner(TableIsOwnerRequest) returns (TableIsOwnerResponse);

  // Deck Management (2 methods)
  rpc DeckSubmit(DeckSubmitRequest) returns (DeckSubmitResponse);
  rpc DeckSave(DeckSaveRequest) returns (DeckSaveResponse);

  // Game Execution (15 methods)
  rpc GameJoin(GameJoinRequest) returns (GameJoinResponse);
  rpc GameWatchStart(GameWatchStartRequest) returns (GameWatchStartResponse);
  rpc GameWatchStop(GameWatchStopRequest) returns (GameWatchStopResponse);
  rpc GameGetView(GameGetViewRequest) returns (GameGetViewResponse);
  rpc SendPlayerUUID(SendPlayerUUIDRequest) returns (SendPlayerUUIDResponse);
  rpc SendPlayerString(SendPlayerStringRequest) returns (SendPlayerStringResponse);
  rpc SendPlayerBoolean(SendPlayerBooleanRequest) returns (SendPlayerBooleanResponse);
  rpc SendPlayerInteger(SendPlayerIntegerRequest) returns (SendPlayerIntegerResponse);
  rpc SendPlayerManaType(SendPlayerManaTypeRequest) returns (SendPlayerManaTypeResponse);
  rpc SendPlayerAction(SendPlayerActionRequest) returns (SendPlayerActionResponse);
  rpc MatchStart(MatchStartRequest) returns (MatchStartResponse);
  rpc MatchQuit(MatchQuitRequest) returns (MatchQuitResponse);

  // Draft (6 methods)
  rpc DraftJoin(DraftJoinRequest) returns (DraftJoinResponse);
  rpc SendDraftCardPick(SendDraftCardPickRequest) returns (SendDraftCardPickResponse);
  rpc SendDraftCardMark(SendDraftCardMarkRequest) returns (SendDraftCardMarkResponse);
  rpc DraftSetBoosterLoaded(DraftSetBoosterLoadedRequest) returns (DraftSetBoosterLoadedResponse);
  rpc DraftQuit(DraftQuitRequest) returns (DraftQuitResponse);

  // Tournament (4 methods)
  rpc TournamentJoin(TournamentJoinRequest) returns (TournamentJoinResponse);
  rpc TournamentStart(TournamentStartRequest) returns (TournamentStartResponse);
  rpc TournamentQuit(TournamentQuitRequest) returns (TournamentQuitResponse);
  rpc TournamentFindById(TournamentFindByIdRequest) returns (TournamentFindByIdResponse);

  // Chat (7 methods)
  rpc ChatJoin(ChatJoinRequest) returns (ChatJoinResponse);
  rpc ChatLeave(ChatLeaveRequest) returns (ChatLeaveResponse);
  rpc ChatSendMessage(ChatSendMessageRequest) returns (ChatSendMessageResponse);
  rpc ChatFindByTable(ChatFindByTableRequest) returns (ChatFindByTableResponse);
  rpc ChatFindByGame(ChatFindByGameRequest) returns (ChatFindByGameResponse);
  rpc ChatFindByTournament(ChatFindByTournamentRequest) returns (ChatFindByTournamentResponse);
  rpc ChatFindByRoom(ChatFindByRoomRequest) returns (ChatFindByRoomResponse);

  // Replay (6 methods)
  rpc ReplayInit(ReplayInitRequest) returns (ReplayInitResponse);
  rpc ReplayStart(ReplayStartRequest) returns (ReplayStartResponse);
  rpc ReplayStop(ReplayStopRequest) returns (ReplayStopResponse);
  rpc ReplayNext(ReplayNextRequest) returns (ReplayNextResponse);
  rpc ReplayPrevious(ReplayPreviousRequest) returns (ReplayPreviousResponse);
  rpc ReplaySkipForward(ReplaySkipForwardRequest) returns (ReplaySkipForwardResponse);

  // Admin (9 methods)
  rpc AdminGetUsers(AdminGetUsersRequest) returns (AdminGetUsersResponse);
  rpc AdminDisconnectUser(AdminDisconnectUserRequest) returns (AdminDisconnectUserResponse);
  rpc AdminMuteUser(AdminMuteUserRequest) returns (AdminMuteUserResponse);
  rpc AdminLockUser(AdminLockUserRequest) returns (AdminLockUserResponse);
  rpc AdminActivateUser(AdminActivateUserRequest) returns (AdminActivateUserResponse);
  rpc AdminToggleActivateUser(AdminToggleActivateUserRequest) returns (AdminToggleActivateUserResponse);
  rpc AdminEndUserSession(AdminEndUserSessionRequest) returns (AdminEndUserSessionResponse);
  rpc AdminTableRemove(AdminTableRemoveRequest) returns (AdminTableRemoveResponse);
  rpc AdminSendBroadcastMessage(AdminSendBroadcastMessageRequest) returns (AdminSendBroadcastMessageResponse);
}
```

**api/proto/mage/v1/websocket.proto** (Server Push Messages):
```protobuf
syntax = "proto3";

package mage.v1;

option go_package = "github.com/magefree/mage-server-go/pkg/proto/mage/v1";

import "google/protobuf/any.proto";

// ClientCallback mirrors Java's ClientCallback class
message ServerEvent {
  string session_id = 1;
  string object_id = 2;  // UUID as string
  CallbackMethod method = 3;
  google.protobuf.Any data = 4;  // Polymorphic payload
  int32 message_id = 5;
  bool compressed = 6;
}

enum CallbackMethod {
  CALLBACK_METHOD_UNSPECIFIED = 0;

  // Messages
  CHATMESSAGE = 1;
  SHOW_USERMESSAGE = 2;
  SERVER_MESSAGE = 3;

  // Table
  JOINED_TABLE = 10;

  // Tournament
  START_TOURNAMENT = 20;
  TOURNAMENT_INIT = 21;
  TOURNAMENT_UPDATE = 22;
  TOURNAMENT_OVER = 23;

  // Draft/Sideboard
  START_DRAFT = 30;
  SIDEBOARD = 31;
  CONSTRUCT = 32;
  DRAFT_OVER = 33;
  DRAFT_INIT = 34;
  DRAFT_PICK = 35;
  DRAFT_UPDATE = 36;

  // Watch
  SHOW_TOURNAMENT = 40;
  WATCHGAME = 41;

  // In-game actions
  VIEW_LIMITED_DECK = 50;
  VIEW_SIDEBOARD = 51;

  // Other
  USER_REQUEST_DIALOG = 60;
  GAME_REDRAW_GUI = 61;

  // Game
  START_GAME = 70;
  GAME_INIT = 71;
  GAME_UPDATE_AND_INFORM = 72;
  GAME_INFORM_PERSONAL = 73;
  GAME_ERROR = 74;
  GAME_UPDATE = 75;
  GAME_TARGET = 76;
  GAME_CHOOSE_ABILITY = 77;
  GAME_CHOOSE_PILE = 78;
  GAME_CHOOSE_CHOICE = 79;
  GAME_ASK = 80;
  GAME_SELECT = 81;
  GAME_PLAY_MANA = 82;
  GAME_PLAY_XMANA = 83;
  GAME_GET_AMOUNT = 84;
  GAME_GET_MULTI_AMOUNT = 85;
  GAME_OVER = 86;
  END_GAME_INFO = 87;

  // Replay
  REPLAY_GAME = 90;
  REPLAY_INIT = 91;
  REPLAY_UPDATE = 92;
  REPLAY_DONE = 93;
}
```

**Action Items**:
- [ ] Define all 60+ request/response message types in separate .proto files
- [ ] Define data models (TableView, GameView, MatchOptions, etc.) in models.proto
- [ ] Define WebSocket event messages in websocket.proto
- [ ] Generate Go code: `buf generate` or `make proto`
- [ ] Commit generated code to git

#### 1.3 Database Schema

**migrations/001_create_users_table.up.sql**:
```sql
CREATE TABLE authorized_users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- Argon2id hashes
    email VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lock_end_time TIMESTAMP,
    chat_lock_end_time TIMESTAMP
);

CREATE INDEX idx_authorized_users_name ON authorized_users(name);
CREATE INDEX idx_authorized_users_email ON authorized_users(email);
```

**migrations/002_create_cards_table.up.sql**:
```sql
CREATE TABLE cards (
    id SERIAL PRIMARY KEY,
    card_number VARCHAR(50),
    set_code VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    card_type VARCHAR(255),
    mana_cost VARCHAR(255),
    power VARCHAR(10),
    toughness VARCHAR(10),
    rules_text TEXT,
    flavor_text TEXT,
    original_text TEXT,
    original_type VARCHAR(255),
    cn BIGINT,
    card_name VARCHAR(255),
    rarity VARCHAR(50),
    card_class_name VARCHAR(255)
);

CREATE INDEX idx_cards_name ON cards(name);
CREATE INDEX idx_cards_set_code ON cards(set_code);
CREATE INDEX idx_cards_cn ON cards(cn);

-- Full-text search
CREATE INDEX idx_cards_name_trgm ON cards USING gin(name gin_trgm_ops);
CREATE INDEX idx_cards_rules_text_trgm ON cards USING gin(rules_text gin_trgm_ops);
```

**migrations/003_create_stats_table.up.sql**:
```sql
CREATE TABLE user_stats (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    matches INTEGER DEFAULT 0,
    tournaments INTEGER DEFAULT 0,
    tourneys_won INTEGER DEFAULT 0,
    tourneys_second INTEGER DEFAULT 0,
    wins INTEGER DEFAULT 0,
    losses INTEGER DEFAULT 0,
    quit_ratio NUMERIC(5,2) DEFAULT 0.0,
    rating NUMERIC(10,2) DEFAULT 1500.0,
    rating_deviation NUMERIC(10,2) DEFAULT 350.0,
    volatility NUMERIC(10,4) DEFAULT 0.06,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_stats_user_name ON user_stats(user_name);
CREATE INDEX idx_user_stats_rating ON user_stats(rating DESC);
```

**migrations/004_create_table_records.up.sql**:
```sql
CREATE TABLE table_records (
    id SERIAL PRIMARY KEY,
    table_id UUID NOT NULL,
    table_name VARCHAR(255),
    game_type VARCHAR(100),
    tournament_type VARCHAR(100),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    duration_seconds INTEGER,
    proto_data BYTEA,  -- Protocol Buffers serialized data
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_table_records_table_id ON table_records(table_id);
CREATE INDEX idx_table_records_start_time ON table_records(start_time DESC);
```

**Action Items**:
- [ ] Create all migration files (up and down)
- [ ] Set up golang-migrate: `brew install golang-migrate`
- [ ] Create initial data seed scripts (cards, expansions, etc.)
- [ ] Test migrations against PostgreSQL

#### 1.4 Configuration Management

**config/config.yaml**:
```yaml
server:
  name: "mage-server"
  grpc:
    address: "0.0.0.0:17171"
    max_concurrent_streams: 1000
    max_connection_age: 1h
  websocket:
    address: "0.0.0.0:17179"
    ping_interval: 30s
    pong_timeout: 10s

  max_sessions: 10000
  lease_period: 5s
  max_idle_seconds: 300
  max_game_threads: 10

database:
  host: "localhost"
  port: 5432
  database: "mage"
  user: "mage"
  password: "${DB_PASSWORD}"  # Environment variable
  sslmode: "disable"
  max_open_conns: 25
  max_idle_conns: 5
  conn_max_lifetime: 5m

validation:
  username:
    min_length: 3
    max_length: 14
    pattern: "^[a-z0-9_]+$"
  password:
    min_length: 8
    max_length: 100

auth:
  mode: "optional"  # "optional" or "required"
  require_email: false
  password_reset_token_ttl: 1h

mail:
  provider: "mailgun"  # "smtp" or "mailgun"
  smtp:
    host: "${SMTP_HOST}"
    port: 587
    user: "${SMTP_USER}"
    password: "${SMTP_PASSWORD}"
    from: "noreply@mage.com"
  mailgun:
    domain: "${MAILGUN_DOMAIN}"
    api_key: "${MAILGUN_API_KEY}"
    from: "noreply@mage.com"

logging:
  level: "info"  # debug, info, warn, error
  format: "json"  # json or console
  output: "stdout"

cache:
  cards:
    enabled: true
    ttl: 24h
    max_size: 100000

plugins:
  game_types:
    - TwoPlayerDuel
    - FreeForAll
    - CommanderFreeForAll
  tournament_types:
    - Constructed
    - BoosterDraft
    - Sealed
  player_types:
    - Human
    - ComputerMAX
    - ComputerDraft

metrics:
  enabled: true
  port: 9090
  path: "/metrics"
```

**internal/config/config.go**:
```go
package config

import (
    "fmt"
    "github.com/spf13/viper"
)

type Config struct {
    Server     ServerConfig     `mapstructure:"server"`
    Database   DatabaseConfig   `mapstructure:"database"`
    Validation ValidationConfig `mapstructure:"validation"`
    Auth       AuthConfig       `mapstructure:"auth"`
    Mail       MailConfig       `mapstructure:"mail"`
    Logging    LoggingConfig    `mapstructure:"logging"`
    Cache      CacheConfig      `mapstructure:"cache"`
    Plugins    PluginConfig     `mapstructure:"plugins"`
    Metrics    MetricsConfig    `mapstructure:"metrics"`
}

type ServerConfig struct {
    Name              string          `mapstructure:"name"`
    GRPC              GRPCConfig      `mapstructure:"grpc"`
    WebSocket         WebSocketConfig `mapstructure:"websocket"`
    MaxSessions       int             `mapstructure:"max_sessions"`
    LeasePeriod       string          `mapstructure:"lease_period"`
    MaxIdleSeconds    int             `mapstructure:"max_idle_seconds"`
    MaxGameThreads    int             `mapstructure:"max_game_threads"`
}

type GRPCConfig struct {
    Address              string `mapstructure:"address"`
    MaxConcurrentStreams int    `mapstructure:"max_concurrent_streams"`
    MaxConnectionAge     string `mapstructure:"max_connection_age"`
}

type WebSocketConfig struct {
    Address      string `mapstructure:"address"`
    PingInterval string `mapstructure:"ping_interval"`
    PongTimeout  string `mapstructure:"pong_timeout"`
}

// ... other config structs

func Load(configPath string) (*Config, error) {
    viper.SetConfigFile(configPath)
    viper.AutomaticEnv()

    if err := viper.ReadInConfig(); err != nil {
        return nil, fmt.Errorf("failed to read config: %w", err)
    }

    var cfg Config
    if err := viper.Unmarshal(&cfg); err != nil {
        return nil, fmt.Errorf("failed to unmarshal config: %w", err)
    }

    return &cfg, nil
}
```

**Action Items**:
- [ ] Implement config loading with Viper
- [ ] Support environment variable overrides
- [ ] Add config validation
- [ ] Create example configs for dev/staging/prod
- [ ] Document all config options

### Phase 2: Core Infrastructure (Week 4-6)

#### 2.1 Database Layer

**internal/repository/db.go**:
```go
package repository

import (
    "context"
    "fmt"
    "time"

    "github.com/jackc/pgx/v5/pgxpool"
    "github.com/magefree/mage-server-go/internal/config"
)

type DB struct {
    Pool *pgxpool.Pool
}

func NewDB(cfg config.DatabaseConfig) (*DB, error) {
    dsn := fmt.Sprintf("postgres://%s:%s@%s:%d/%s?sslmode=%s",
        cfg.User, cfg.Password, cfg.Host, cfg.Port, cfg.Database, cfg.SSLMode)

    poolConfig, err := pgxpool.ParseConfig(dsn)
    if err != nil {
        return nil, fmt.Errorf("failed to parse database config: %w", err)
    }

    poolConfig.MaxConns = int32(cfg.MaxOpenConns)
    poolConfig.MinConns = int32(cfg.MaxIdleConns)
    poolConfig.MaxConnLifetime = cfg.ConnMaxLifetime

    pool, err := pgxpool.NewWithConfig(context.Background(), poolConfig)
    if err != nil {
        return nil, fmt.Errorf("failed to create connection pool: %w", err)
    }

    if err := pool.Ping(context.Background()); err != nil {
        return nil, fmt.Errorf("failed to ping database: %w", err)
    }

    return &DB{Pool: pool}, nil
}

func (db *DB) Close() {
    db.Pool.Close()
}
```

**Action Items**:
- [ ] Implement connection pooling with pgx
- [ ] Create repository interfaces for each entity
- [ ] Implement user repository (CRUD + queries)
- [ ] Implement card repository with full-text search
- [ ] Implement stats repository with Glicko rating queries
- [ ] Implement table records repository
- [ ] Add database health check
- [ ] Add query logging for debugging
- [ ] Write repository unit tests with test database

#### 2.2 Session Management

**internal/session/session.go**:
```go
package session

import (
    "sync"
    "time"

    pb "github.com/magefree/mage-server-go/pkg/proto/mage/v1"
)

type Session struct {
    ID           string
    UserID       string
    Host         string
    IsAdmin      bool
    Connected    bool
    LastActivity time.Time
    LeasePeriod  time.Duration
    CallbackChan chan *pb.ServerEvent
    mu           sync.RWMutex
    reqMu        sync.Mutex  // Prevents concurrent requests for same session
}

func NewSession(id, host string, leasePeriod time.Duration) *Session {
    return &Session{
        ID:           id,
        Host:         host,
        Connected:    true,
        LastActivity: time.Now(),
        LeasePeriod:  leasePeriod,
        CallbackChan: make(chan *pb.ServerEvent, 100),
    }
}

func (s *Session) UpdateActivity() {
    s.mu.Lock()
    defer s.mu.Unlock()
    s.LastActivity = time.Now()
}

func (s *Session) IsExpired() bool {
    s.mu.RLock()
    defer s.mu.RUnlock()
    return time.Since(s.LastActivity) > s.LeasePeriod
}

func (s *Session) SendCallback(event *pb.ServerEvent) bool {
    select {
    case s.CallbackChan <- event:
        return true
    case <-time.After(5 * time.Second):
        return false  // Timeout, client not reading
    }
}
```

**internal/session/manager.go**:
```go
package session

import (
    "context"
    "sync"
    "time"
)

type Manager interface {
    CreateSession(id, host string) *Session
    GetSession(id string) (*Session, bool)
    RemoveSession(id string)
    ValidateSession(id string) bool
    UpdateActivity(id string)
    CleanupExpiredSessions(ctx context.Context)
}

type manager struct {
    sessions    map[string]*Session
    mu          sync.RWMutex
    leasePeriod time.Duration
}

func NewManager(leasePeriod time.Duration) Manager {
    return &manager{
        sessions:    make(map[string]*Session),
        leasePeriod: leasePeriod,
    }
}

func (m *manager) CreateSession(id, host string) *Session {
    m.mu.Lock()
    defer m.mu.Unlock()

    sess := NewSession(id, host, m.leasePeriod)
    m.sessions[id] = sess
    return sess
}

func (m *manager) GetSession(id string) (*Session, bool) {
    m.mu.RLock()
    defer m.mu.RUnlock()
    sess, ok := m.sessions[id]
    return sess, ok
}

// Cleanup goroutine runs every leasePeriod/2
func (m *manager) CleanupExpiredSessions(ctx context.Context) {
    ticker := time.NewTicker(m.leasePeriod / 2)
    defer ticker.Stop()

    for {
        select {
        case <-ctx.Done():
            return
        case <-ticker.C:
            m.cleanupExpired()
        }
    }
}

func (m *manager) cleanupExpired() {
    m.mu.Lock()
    defer m.mu.Unlock()

    for id, sess := range m.sessions {
        if sess.IsExpired() {
            close(sess.CallbackChan)
            delete(m.sessions, id)
        }
    }
}
```

**Action Items**:
- [ ] Implement Session struct with all methods
- [ ] Implement SessionManager with in-memory storage
- [ ] Add session expiration cleanup goroutine
- [ ] Implement session restoration for reconnects
- [ ] Add concurrent request locking per session
- [ ] Write session manager tests
- [ ] (Future) Add Redis-backed session store interface

#### 2.3 Authentication & Security

**internal/auth/password.go**:
```go
package auth

import (
    "crypto/rand"
    "encoding/base64"
    "fmt"
    "strings"

    "golang.org/x/crypto/argon2"
)

const (
    // Argon2id parameters
    argon2Time    = 1
    argon2Memory  = 64 * 1024  // 64 MB
    argon2Threads = 4
    argon2KeyLen  = 32
    argon2SaltLen = 16
)

// HashPassword creates Argon2id hash for passwords
func HashPassword(password string) (string, error) {
    salt := make([]byte, argon2SaltLen)
    if _, err := rand.Read(salt); err != nil {
        return "", err
    }

    hash := argon2.IDKey([]byte(password), salt, argon2Time, argon2Memory, argon2Threads, argon2KeyLen)

    // Format: $argon2id$v=19$m=65536,t=1,p=4$<base64salt>$<base64hash>
    b64Salt := base64.RawStdEncoding.EncodeToString(salt)
    b64Hash := base64.RawStdEncoding.EncodeToString(hash)

    return fmt.Sprintf("$argon2id$v=19$m=%d,t=%d,p=%d$%s$%s",
        argon2Memory, argon2Time, argon2Threads, b64Salt, b64Hash), nil
}

// VerifyPassword verifies Argon2id password hash
func VerifyPassword(password, hash string) bool {
    parts := strings.Split(hash, "$")
    if len(parts) != 6 || parts[1] != "argon2id" {
        return false
    }

    salt, _ := base64.RawStdEncoding.DecodeString(parts[4])
    hashBytes, _ := base64.RawStdEncoding.DecodeString(parts[5])

    compareHash := argon2.IDKey([]byte(password), salt, argon2Time, argon2Memory, argon2Threads, argon2KeyLen)

    return subtle.ConstantTimeCompare(hashBytes, compareHash) == 1
}
```

**Action Items**:
- [ ] Implement Argon2id password hashing
- [ ] Implement password reset token generation (6-digit)
- [ ] Add token storage (in-memory cache with TTL)
- [ ] Write password hashing tests
- [ ] Write auth service tests

### Phase 3: gRPC Server Implementation (Week 7-10)

#### 3.1 gRPC Server Bootstrap

**cmd/server/main.go**:
```go
package main

import (
    "context"
    "fmt"
    "net"
    "os"
    "os/signal"
    "syscall"
    "time"

    "github.com/magefree/mage-server-go/internal/config"
    "github.com/magefree/mage-server-go/internal/repository"
    "github.com/magefree/mage-server-go/internal/server"
    "github.com/magefree/mage-server-go/internal/session"
    pb "github.com/magefree/mage-server-go/pkg/proto/mage/v1"
    "go.uber.org/zap"
    "google.golang.org/grpc"
    "google.golang.org/grpc/keepalive"
)

func main() {
    // Load config
    cfg, err := config.Load("config/config.yaml")
    if err != nil {
        panic(fmt.Sprintf("failed to load config: %v", err))
    }

    // Initialize logger
    logger, _ := zap.NewProduction()
    defer logger.Sync()

    // Initialize database
    db, err := repository.NewDB(cfg.Database)
    if err != nil {
        logger.Fatal("failed to connect to database", zap.Error(err))
    }
    defer db.Close()

    // Initialize session manager
    sessionMgr := session.NewManager(5 * time.Second)

    // Start session cleanup goroutine
    ctx, cancel := context.WithCancel(context.Background())
    defer cancel()
    go sessionMgr.CleanupExpiredSessions(ctx)

    // Initialize server implementation
    mageServer := server.NewMageServer(cfg, db, sessionMgr, logger)

    // Create gRPC server with interceptors
    grpcServer := grpc.NewServer(
        grpc.UnaryInterceptor(server.ChainUnaryInterceptors(
            server.SessionValidationInterceptor(sessionMgr),
            server.LoggingInterceptor(logger),
            server.RecoveryInterceptor(logger),
        )),
        grpc.KeepaliveParams(keepalive.ServerParameters{
            Time:    30 * time.Second,
            Timeout: 10 * time.Second,
        }),
        grpc.MaxConcurrentStreams(uint32(cfg.Server.GRPC.MaxConcurrentStreams)),
    )

    pb.RegisterMageServerServer(grpcServer, mageServer)

    // Start gRPC listener
    lis, err := net.Listen("tcp", cfg.Server.GRPC.Address)
    if err != nil {
        logger.Fatal("failed to listen", zap.Error(err))
    }

    // Start WebSocket server in separate goroutine
    go server.StartWebSocketServer(cfg.Server.WebSocket, sessionMgr, logger)

    // Graceful shutdown
    sigChan := make(chan os.Signal, 1)
    signal.Notify(sigChan, os.Interrupt, syscall.SIGTERM)
    go func() {
        <-sigChan
        logger.Info("shutting down gracefully...")
        grpcServer.GracefulStop()
        cancel()
    }()

    // Start serving
    logger.Info("starting MAGE server",
        zap.String("grpc", cfg.Server.GRPC.Address),
        zap.String("websocket", cfg.Server.WebSocket.Address))

    if err := grpcServer.Serve(lis); err != nil {
        logger.Fatal("failed to serve", zap.Error(err))
    }
}
```

**Action Items**:
- [ ] Implement main entry point with graceful shutdown
- [ ] Set up gRPC server with keepalive
- [ ] Implement session validation interceptor
- [ ] Implement logging interceptor
- [ ] Implement panic recovery interceptor
- [ ] Implement metrics interceptor (Prometheus)
- [ ] Add health check service

#### 3.2 RPC Method Implementation (60+ methods)

**internal/server/grpc.go** (Core structure):
```go
package server

import (
    "context"

    "github.com/magefree/mage-server-go/internal/auth"
    "github.com/magefree/mage-server-go/internal/chat"
    "github.com/magefree/mage-server-go/internal/config"
    "github.com/magefree/mage-server-go/internal/draft"
    "github.com/magefree/mage-server-go/internal/game"
    "github.com/magefree/mage-server-go/internal/repository"
    "github.com/magefree/mage-server-go/internal/room"
    "github.com/magefree/mage-server-go/internal/session"
    "github.com/magefree/mage-server-go/internal/table"
    "github.com/magefree/mage-server-go/internal/tournament"
    "github.com/magefree/mage-server-go/internal/user"
    pb "github.com/magefree/mage-server-go/pkg/proto/mage/v1"
    "go.uber.org/zap"
)

type mageServer struct {
    pb.UnimplementedMageServerServer

    config        *config.Config
    logger        *zap.Logger

    sessionMgr    session.Manager
    userMgr       user.Manager
    authSvc       auth.Service
    roomMgr       room.Manager
    tableMgr      table.Manager
    gameMgr       game.Manager
    tournamentMgr tournament.Manager
    draftMgr      draft.Manager
    chatMgr       chat.Manager

    db            *repository.DB
}

func NewMageServer(
    cfg *config.Config,
    db *repository.DB,
    sessionMgr session.Manager,
    logger *zap.Logger,
) pb.MageServerServer {
    // Initialize all managers
    userRepo := repository.NewUserRepository(db)
    userMgr := user.NewManager(userRepo, cfg.Validation)
    authSvc := auth.NewService(userRepo, cfg.Auth)

    // ... initialize other managers

    return &mageServer{
        config:        cfg,
        logger:        logger,
        sessionMgr:    sessionMgr,
        userMgr:       userMgr,
        authSvc:       authSvc,
        // ... assign other managers
        db:            db,
    }
}
```

**Implement Authentication Methods** (internal/server/auth.go):
```go
func (s *mageServer) ConnectUser(ctx context.Context, req *pb.ConnectUserRequest) (*pb.ConnectUserResponse, error) {
    // Extract peer info for IP address
    host := extractHostFromContext(ctx)

    // Validate credentials
    userID, err := s.authSvc.ValidateCredentials(ctx, req.UserName, req.Password)
    if err != nil {
        return &pb.ConnectUserResponse{Success: false, Error: err.Error()}, nil
    }

    // Create or restore session
    var sess *session.Session
    if req.RestoreSessionId != "" {
        sess, _ = s.sessionMgr.GetSession(req.RestoreSessionId)
    }
    if sess == nil {
        sess = s.sessionMgr.CreateSession(req.SessionId, host)
    }

    sess.UserID = userID
    sess.UpdateActivity()

    // Register user as connected
    if err := s.userMgr.UserConnect(ctx, userID, sess.ID); err != nil {
        return &pb.ConnectUserResponse{Success: false, Error: err.Error()}, nil
    }

    s.logger.Info("user connected",
        zap.String("user", req.UserName),
        zap.String("session", sess.ID),
        zap.String("host", host))

    return &pb.ConnectUserResponse{Success: true}, nil
}

func (s *mageServer) Ping(ctx context.Context, req *pb.PingRequest) (*pb.PingResponse, error) {
    s.sessionMgr.UpdateActivity(req.SessionId)
    return &pb.PingResponse{Success: true}, nil
}

func (s *mageServer) AuthRegister(ctx context.Context, req *pb.AuthRegisterRequest) (*pb.AuthRegisterResponse, error) {
    err := s.authSvc.Register(ctx, req.UserName, req.Password, req.Email)
    if err != nil {
        return &pb.AuthRegisterResponse{Success: false, Error: err.Error()}, nil
    }
    return &pb.AuthRegisterResponse{Success: true}, nil
}

// ... implement remaining 57 methods
```

**Action Items**:
- [ ] Implement all Authentication methods (6 methods)
- [ ] Implement all Server Info methods (3 methods)
- [ ] Implement all Room/Lobby methods (5 methods)
- [ ] Implement all Table Management methods (10 methods)
- [ ] Implement all Deck Management methods (2 methods)
- [ ] Implement all Game Execution methods (15 methods)
- [ ] Implement all Draft methods (6 methods)
- [ ] Implement all Tournament methods (4 methods)
- [ ] Implement all Chat methods (7 methods)
- [ ] Implement all Replay methods (6 methods)
- [ ] Implement all Admin methods (9 methods)
- [ ] Add error handling and validation for each method
- [ ] Write integration tests for each category

### Phase 4: WebSocket Server (Week 11-12)

#### 4.1 WebSocket Implementation

**internal/server/websocket.go**:
```go
package server

import (
    "context"
    "encoding/json"
    "net/http"
    "time"

    "github.com/gorilla/websocket"
    "github.com/magefree/mage-server-go/internal/config"
    "github.com/magefree/mage-server-go/internal/session"
    pb "github.com/magefree/mage-server-go/pkg/proto/mage/v1"
    "go.uber.org/zap"
    "google.golang.org/protobuf/encoding/protojson"
)

var upgrader = websocket.Upgrader{
    CheckOrigin: func(r *http.Request) bool {
        return true  // TODO: Add proper origin validation
    },
}

type WebSocketServer struct {
    sessionMgr session.Manager
    logger     *zap.Logger
    config     config.WebSocketConfig
}

func StartWebSocketServer(cfg config.WebSocketConfig, sessionMgr session.Manager, logger *zap.Logger) {
    ws := &WebSocketServer{
        sessionMgr: sessionMgr,
        logger:     logger,
        config:     cfg,
    }

    http.HandleFunc("/ws", ws.handleConnection)

    logger.Info("starting WebSocket server", zap.String("address", cfg.Address))
    if err := http.ListenAndServe(cfg.Address, nil); err != nil {
        logger.Fatal("WebSocket server failed", zap.Error(err))
    }
}

func (ws *WebSocketServer) handleConnection(w http.ResponseWriter, r *http.Request) {
    // Extract session ID from query params or headers
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

    ws.logger.Info("WebSocket connected", zap.String("session", sessionID))

    // Start ping/pong handler
    go ws.pingHandler(conn, sess)

    // Read callback channel and send to client
    for event := range sess.CallbackChan {
        if err := ws.sendEvent(conn, event); err != nil {
            ws.logger.Error("failed to send event", zap.Error(err), zap.String("session", sessionID))
            break
        }
    }

    ws.logger.Info("WebSocket disconnected", zap.String("session", sessionID))
}

func (ws *WebSocketServer) sendEvent(conn *websocket.Conn, event *pb.ServerEvent) error {
    // Convert protobuf to JSON for easier client handling
    jsonData, err := protojson.Marshal(event)
    if err != nil {
        return err
    }

    conn.SetWriteDeadline(time.Now().Add(10 * time.Second))
    return conn.WriteMessage(websocket.TextMessage, jsonData)
}

func (ws *WebSocketServer) pingHandler(conn *websocket.Conn, sess *session.Session) {
    ticker := time.NewTicker(30 * time.Second)
    defer ticker.Stop()

    for range ticker.C {
        conn.SetWriteDeadline(time.Now().Add(10 * time.Second))
        if err := conn.WriteMessage(websocket.PingMessage, nil); err != nil {
            return
        }
    }
}
```

**Action Items**:
- [ ] Implement WebSocket server with Gorilla WebSocket
- [ ] Handle WebSocket upgrade from HTTP
- [ ] Implement session validation for WebSocket connections
- [ ] Forward ServerEvent messages from session.CallbackChan to WebSocket
- [ ] Implement ping/pong keep-alive
- [ ] Add reconnection handling (resume from last message ID)
- [ ] Implement compression for large messages
- [ ] Write WebSocket integration tests
- [ ] Document WebSocket protocol for client developers

### Phase 5: Business Logic - Managers & Controllers (Week 13-18)

#### 5.1 User Management
**Action Items**:
- [ ] Implement User domain model
- [ ] Implement UserManager interface and implementation
- [ ] Implement user registration (anonymous and authenticated modes)
- [ ] Implement username validation and uniqueness checks
- [ ] Implement multiple connection detection
- [ ] Implement lock/mute/activate operations
- [ ] Write user manager tests

#### 5.2 Table Controller
**Action Items**:
- [ ] Implement TableController
- [ ] Implement table state machine (WAITING → STARTING → DUELING → FINISHED)
- [ ] Implement player seat assignment and swapping
- [ ] Implement deck validation hooks
- [ ] Implement match/tournament creation logic
- [ ] Integrate with game/tournament controllers
- [ ] Write table controller tests

#### 5.3 Game Controller
**Action Items**:
- [ ] Implement GameController
- [ ] Implement game state management
- [ ] Implement player action queue and processing
- [ ] Implement GameView generation for clients
- [ ] Implement watcher management
- [ ] Implement replay recording
- [ ] Define game engine integration interface
- [ ] Write game controller tests (with mock game engine)

#### 5.4 Tournament System
**Action Items**:
- [ ] Implement TournamentController
- [ ] Implement tournament state machine
- [ ] Implement round management
- [ ] Implement Swiss pairing algorithm
- [ ] Implement elimination bracket generation
- [ ] Implement TournamentView generation
- [ ] Integrate with draft system
- [ ] Write tournament controller tests

#### 5.5 Draft System
**Action Items**:
- [ ] Implement DraftController
- [ ] Implement draft state machine (pick phase, pass direction)
- [ ] Implement booster pack generation from card repository
- [ ] Implement card pick handling
- [ ] Implement hidden card management (cards not yet seen)
- [ ] Implement DraftPickView generation
- [ ] Write draft controller tests

#### 5.6 Chat System
**Action Items**:
- [ ] Implement ChatManager
- [ ] Implement chat room management (game, tournament, table, lobby)
- [ ] Implement message broadcasting to all room members
- [ ] Implement message history (configurable limit)
- [ ] Implement user join/leave notifications
- [ ] Implement HTML sanitization (bluemonday)
- [ ] Write chat manager tests

#### 5.7 Room Management
**Action Items**:
- [ ] Implement GamesRoom (main lobby)
- [ ] Implement lobby features (user list, table list, finished matches)
- [ ] Implement room update broadcasting
- [ ] Write room manager tests

### Phase 6: Plugin System & Game Types (Week 19-21)

#### 6.1 Plugin Registry Pattern

**internal/plugin/registry.go**:
```go
package plugin

import (
    "fmt"
    "sync"
)

type GameType interface {
    Name() string
    MinPlayers() int
    MaxPlayers() int
    // ... other game type methods
}

type TournamentType interface {
    Name() string
    // ... tournament type methods
}

type PlayerType interface {
    Name() string
    IsAI() bool
    // ... player type methods
}

type Registry struct {
    gameTypes       map[string]GameType
    tournamentTypes map[string]TournamentType
    playerTypes     map[string]PlayerType
    mu              sync.RWMutex
}

var defaultRegistry = &Registry{
    gameTypes:       make(map[string]GameType),
    tournamentTypes: make(map[string]TournamentType),
    playerTypes:     make(map[string]PlayerType),
}

func RegisterGameType(gt GameType) {
    defaultRegistry.mu.Lock()
    defer defaultRegistry.mu.Unlock()
    defaultRegistry.gameTypes[gt.Name()] = gt
}

func GetGameType(name string) (GameType, error) {
    defaultRegistry.mu.RLock()
    defer defaultRegistry.mu.RUnlock()
    gt, ok := defaultRegistry.gameTypes[name]
    if !ok {
        return nil, fmt.Errorf("game type not found: %s", name)
    }
    return gt, nil
}

// ... similar for tournament and player types
```

**internal/plugin/game_types.go** (Example game type):
```go
package plugin

type TwoPlayerDuel struct{}

func init() {
    RegisterGameType(&TwoPlayerDuel{})
}

func (g *TwoPlayerDuel) Name() string {
    return "Two Player Duel"
}

func (g *TwoPlayerDuel) MinPlayers() int { return 2 }
func (g *TwoPlayerDuel) MaxPlayers() int { return 2 }

// ... implement other methods
```

**Action Items**:
- [ ] Implement plugin registry pattern
- [ ] Define GameType, TournamentType, PlayerType interfaces
- [ ] Implement game types:
  - [ ] TwoPlayerDuel
  - [ ] FreeForAll
  - [ ] CommanderFreeForAll
  - [ ] CommanderDuel
  - [ ] Brawl variants
  - [ ] Canadian Highlander
  - [ ] Momir variants
  - [ ] Oathbreaker variants
  - [ ] Penny Dreadful Commander
  - [ ] Tiny Leaders
- [ ] Implement tournament types:
  - [ ] Constructed
  - [ ] BoosterDraft
  - [ ] Sealed
- [ ] Implement player types:
  - [ ] Human
  - [ ] ComputerMAX (AI)
  - [ ] ComputerDraft (Draft AI)
- [ ] Register all types in init() functions
- [ ] Write plugin registry tests

### Phase 7: Supporting Services (Week 22-23)

#### 7.1 Email Service
**Action Items**:
- [ ] Implement MailClient interface
- [ ] Implement SMTP client with gomail.v2
- [ ] Implement Mailgun client with mailgun-go
- [ ] Create email templates (password reset, welcome, etc.)
- [ ] Add retry logic for failed emails
- [ ] Write email service tests (with mock SMTP server)

#### 7.2 Rating System
**Action Items**:
- [ ] Implement Glicko rating calculation
- [ ] Implement rating update on match completion
- [ ] Write rating system tests with known inputs/outputs

#### 7.3 Card Repository & Caching
**Action Items**:
- [ ] Implement full-text search for cards (PostgreSQL trgm)
- [ ] Implement card caching with groupcache
- [ ] Add cache warming on startup
- [ ] Write card repository tests

#### 7.4 Logging & Metrics
**Action Items**:
- [ ] Configure Zap logger with structured fields
- [ ] Add contextual logging (session ID, user ID, game ID)
- [ ] Implement Prometheus metrics:
  - [ ] Active sessions gauge
  - [ ] Active games gauge
  - [ ] RPC request counter
  - [ ] RPC latency histogram
  - [ ] Database query latency
  - [ ] WebSocket connections gauge
- [ ] Create Grafana dashboard JSON
- [ ] Write metrics tests

### Phase 8: Testing & Quality (Week 24-26)

#### 8.1 Unit Tests
**Action Items**:
- [ ] Achieve 70%+ code coverage
- [ ] Test all repositories with test database
- [ ] Test all managers with mocks
- [ ] Test all controllers with mocks
- [ ] Test authentication flows
- [ ] Test session management
- [ ] Test rating calculations
- [ ] Set up test fixtures and helpers

#### 8.2 Integration Tests
**Action Items**:
- [ ] Test complete user registration → login → game flow
- [ ] Test session lifecycle (connect → ping → timeout)
- [ ] Test WebSocket callback delivery
- [ ] Test multi-player game creation and joining
- [ ] Test tournament creation and pairing
- [ ] Test draft flow (join → pick → complete)
- [ ] Test chat message broadcasting
- [ ] Test admin operations
- [ ] Set up integration test environment (Docker Compose)

#### 8.3 Performance & Load Testing
**Action Items**:
- [ ] Create load testing tool (simulated clients)
- [ ] Test 100 concurrent users
- [ ] Test 500 concurrent users
- [ ] Test 1000 concurrent users
- [ ] Profile with pprof (CPU, memory, goroutines)
- [ ] Optimize database queries (add indexes where needed)
- [ ] Optimize hot paths identified by profiling
- [ ] Benchmark critical functions

#### 8.4 Client Compatibility Testing
**Action Items**:
- [ ] Create Java client adapter for gRPC/WebSocket
- [ ] Test all 60+ RPC methods from Java client
- [ ] Test callback delivery to Java client
- [ ] Test complete game flow with Java client
- [ ] Document client integration guide
- [ ] Create example client code (Java and potentially others)

### Phase 9: Deployment & Documentation (Week 27-28)

#### 9.1 Containerization
**Action Items**:
- [ ] Create multi-stage Dockerfile
  ```dockerfile
  FROM golang:1.21 AS builder
  WORKDIR /build
  COPY . .
  RUN make build

  FROM debian:bookworm-slim
  RUN apt-get update && apt-get install -y ca-certificates
  COPY --from=builder /build/bin/mage-server /usr/local/bin/
  COPY config/config.yaml /etc/mage/config.yaml
  EXPOSE 17171 17179
  CMD ["mage-server", "-config", "/etc/mage/config.yaml"]
  ```
- [ ] Create Docker Compose for local development
  ```yaml
  version: '3.8'
  services:
    postgres:
      image: postgres:15
      environment:
        POSTGRES_DB: mage
        POSTGRES_USER: mage
        POSTGRES_PASSWORD: mage
      volumes:
        - postgres-data:/var/lib/postgresql/data

    mage-server:
      build: .
      ports:
        - "17171:17171"
        - "17179:17179"
      depends_on:
        - postgres
      environment:
        DB_HOST: postgres
        DB_PASSWORD: mage
  ```
- [ ] Add health check endpoint
- [ ] Implement graceful shutdown (drain connections)
- [ ] Test container deployment

#### 9.2 Monitoring & Observability
**Action Items**:
- [ ] Set up Prometheus scraping
- [ ] Create Grafana dashboards:
  - [ ] Server health (CPU, memory, goroutines)
  - [ ] Business metrics (active users, games, sessions)
  - [ ] RPC performance (latency, errors)
  - [ ] Database performance
- [ ] Set up alerting rules:
  - [ ] High error rate
  - [ ] High latency
  - [ ] Database connection failures
  - [ ] Memory leaks (goroutine growth)
- [ ] Add distributed tracing (optional: Jaeger)

#### 9.3 Documentation
**Action Items**:
- [ ] Write README.md for Go server
- [ ] Document architecture and design decisions
- [ ] Document configuration options
- [ ] Write deployment guide
- [ ] Write client integration guide (Java client → gRPC/WebSocket)
- [ ] Document API (generate from protobuf)
- [ ] Create troubleshooting guide
- [ ] Write developer onboarding guide

## Technology Stack Summary

### Core Technologies
- **Language**: Go 1.21+
- **RPC**: gRPC (google.golang.org/grpc)
- **Real-time**: WebSocket (github.com/gorilla/websocket)
- **Serialization**: Protocol Buffers (google.golang.org/protobuf)
- **Database**: PostgreSQL 15+ (github.com/jackc/pgx/v5)

### Libraries
- **Config**: Viper (github.com/spf13/viper)
- **Logging**: Zap (go.uber.org/zap)
- **Password**: Argon2id (golang.org/x/crypto/argon2)
- **Email**: gomail.v2 + mailgun-go/v4
- **Cache**: groupcache (github.com/golang/groupcache)
- **Validation**: validator/v10 (github.com/go-playground/validator)
- **Testing**: testify (github.com/stretchr/testify)
- **Metrics**: Prometheus (github.com/prometheus/client_golang)
- **Sanitization**: bluemonday (github.com/microcosm-cc/bluemonday)
- **Database migrations**: golang-migrate (github.com/golang-migrate/migrate)

### Development Tools
- **Protobuf**: buf (buf.build) or protoc
- **Linting**: golangci-lint
- **Formatting**: gofmt, goimports
- **Build**: Make
- **Container**: Docker, Docker Compose

## Timeline Estimate

### Detailed Timeline
- **Phase 1**: Foundation & Protobuf (3 weeks) - Weeks 1-3
- **Phase 2**: Core Infrastructure (3 weeks) - Weeks 4-6
- **Phase 3**: gRPC Server (4 weeks) - Weeks 7-10
- **Phase 4**: WebSocket Server (2 weeks) - Weeks 11-12
- **Phase 5**: Business Logic (6 weeks) - Weeks 13-18
- **Phase 6**: Plugin System (3 weeks) - Weeks 19-21
- **Phase 7**: Supporting Services (2 weeks) - Weeks 22-23
- **Phase 8**: Testing & Quality (3 weeks) - Weeks 24-26
- **Phase 9**: Deployment (2 weeks) - Weeks 27-28

**Total**: 28 weeks (~7 months) with 2-3 developers

### Parallel Work Opportunities
- **Weeks 1-3**: Dev 1 (Protobuf), Dev 2 (Database schemas), Dev 3 (Config)
- **Weeks 4-6**: Dev 1 (Repositories), Dev 2 (Session mgmt), Dev 3 (Auth)
- **Weeks 7-18**: Dev 1 (Game/Table), Dev 2 (Tournament/Draft), Dev 3 (RPC implementation)

## Success Metrics

- [ ] All 60+ RPC methods implemented and tested
- [ ] WebSocket push events working for all callback types
- [ ] Java client successfully connects and plays games
- [ ] Performance: Handle 1000+ concurrent users
- [ ] Latency: p95 < 100ms for game actions, p99 < 500ms
- [ ] Memory: < 2GB RAM for 500 concurrent games
- [ ] All game types functional
- [ ] 70%+ code coverage
- [ ] Load test: 1000 concurrent users for 1 hour without crashes

## Risk Mitigation

### High-Risk Areas
1. **Game Engine Integration**: Server is separate from game engine
2. **Callback Delivery**: Real-time push is critical for UX
3. **Session Management**: Lease mechanism must be rock-solid
4. **Client Compatibility**: Java client must work with minimal changes

### Mitigation Strategies
- **Feature Flags**: Gradual rollout of features
- **Extensive Testing**: Integration tests for all critical flows
- **Client Adapter**: Create compatibility layer in Java client to minimize code changes
- **Performance Testing**: Early and continuous load testing

## Next Steps for Development Agent

### Immediate Actions (Week 1)
1. Initialize Go project: `go mod init github.com/magefree/mage-server-go`
2. Set up project structure (create directories per structure above)
3. Create Makefile with targets: `build`, `test`, `proto`, `run`
4. Define all protobuf schemas in `api/proto/mage/v1/`
5. Generate Go code: `buf generate` or `make proto`
6. Create initial config.yaml with all settings
7. Implement config loader with Viper
8. Set up PostgreSQL schema migrations (create all .up.sql and .down.sql files)
9. Implement database connection with pgx
10. Create basic main.go that loads config and connects to database

### Development Priorities
1. **Critical Path**: Protobuf → Database → Session → Auth → Core RPC methods
2. **Early Wins**: Get ConnectUser, Ping, and basic session management working first
3. **Incremental**: Implement one RPC category at a time, test thoroughly
4. **Client Feedback Loop**: Create simple test client early to validate RPC responses

This plan is complete and ready for implementation. The coding agent can proceed with Phase 1 immediately.
