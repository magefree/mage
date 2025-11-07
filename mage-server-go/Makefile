.PHONY: build test proto run clean docker lint fmt help

# Variables
BINARY_NAME=mage-server
MAIN_PATH=./cmd/server
BUILD_DIR=./bin
PROTO_DIR=./api/proto
PKG_PROTO_DIR=./pkg/proto

# Go parameters
GOCMD=go
GOBUILD=$(GOCMD) build
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
GOMOD=$(GOCMD) mod
GOFMT=$(GOCMD) fmt
GOVET=$(GOCMD) vet

# Build flags
LDFLAGS=-ldflags "-s -w"

help: ## Display this help screen
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

build: ## Build the server binary
	@echo "Building $(BINARY_NAME)..."
	@mkdir -p $(BUILD_DIR)
	$(GOBUILD) $(LDFLAGS) -o $(BUILD_DIR)/$(BINARY_NAME) $(MAIN_PATH)
	@echo "Build complete: $(BUILD_DIR)/$(BINARY_NAME)"

run: build ## Build and run the server
	@echo "Running $(BINARY_NAME)..."
	$(BUILD_DIR)/$(BINARY_NAME) -config config/config.yaml

test: ## Run tests
	@echo "Running tests..."
	$(GOTEST) -v -race -coverprofile=coverage.out ./...
	@echo "Test coverage:"
	$(GOCMD) tool cover -func=coverage.out

test-integration: ## Run integration tests
	@echo "Running integration tests..."
	$(GOTEST) -v -race -tags=integration ./test/integration/...

proto: ## Generate protobuf code
	@echo "Generating protobuf code..."
	@./scripts/generate_proto.sh
	@echo "Protobuf generation complete"

clean: ## Clean build artifacts
	@echo "Cleaning..."
	@rm -rf $(BUILD_DIR)
	@rm -f coverage.out
	@echo "Clean complete"

fmt: ## Format code
	@echo "Formatting code..."
	$(GOFMT) ./...
	@goimports -w .

lint: ## Run linters
	@echo "Running linters..."
	@golangci-lint run ./...

vet: ## Run go vet
	@echo "Running go vet..."
	$(GOVET) ./...

deps: ## Download dependencies
	@echo "Downloading dependencies..."
	$(GOMOD) download
	$(GOMOD) tidy

docker-build: ## Build Docker image
	@echo "Building Docker image..."
	docker build -t mage-server:latest .

docker-run: docker-build ## Run Docker container
	@echo "Running Docker container..."
	docker-compose up

migrate-up: ## Run database migrations up
	@echo "Running migrations up..."
	migrate -path migrations -database "postgresql://mage:mage@localhost:5432/mage?sslmode=disable" up

migrate-down: ## Run database migrations down
	@echo "Running migrations down..."
	migrate -path migrations -database "postgresql://mage:mage@localhost:5432/mage?sslmode=disable" down

migrate-create: ## Create new migration (usage: make migrate-create NAME=create_users)
	@echo "Creating migration: $(NAME)"
	migrate create -ext sql -dir migrations -seq $(NAME)

tools: ## Install development tools
	@echo "Installing development tools..."
	@go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
	@go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest
	@go install github.com/golangci/golangci-lint/cmd/golangci-lint@latest
	@go install golang.org/x/tools/cmd/goimports@latest
	@echo "Tools installed"

.DEFAULT_GOAL := help
