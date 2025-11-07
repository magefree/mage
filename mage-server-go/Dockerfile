# Multi-stage Docker build for MAGE Go server

# Stage 1: Build
FROM golang:1.21-alpine AS builder

# Install build dependencies
RUN apk add --no-cache git make protoc protobuf-dev

# Set working directory
WORKDIR /build

# Copy go mod files
COPY go.mod go.sum ./

# Download dependencies
RUN go mod download

# Copy source code
COPY . .

# Install protoc plugins
RUN go install google.golang.org/protobuf/cmd/protoc-gen-go@latest && \
    go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest

# Generate protobuf code (if needed)
RUN make proto || true

# Build the server
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build \
    -ldflags="-s -w -X main.version=$(git describe --tags --always --dirty 2>/dev/null || echo 'dev')" \
    -o /build/bin/mage-server \
    ./cmd/server

# Stage 2: Runtime
FROM alpine:latest

# Install ca-certificates for HTTPS
RUN apk --no-cache add ca-certificates

# Create non-root user
RUN addgroup -S mage && adduser -S mage -G mage

# Set working directory
WORKDIR /app

# Copy binary from builder
COPY --from=builder /build/bin/mage-server /app/mage-server

# Copy configuration files
COPY --from=builder /build/config/config.yaml /app/config/config.yaml
COPY --from=builder /build/config/config.example.yaml /app/config/config.example.yaml

# Copy migrations
COPY --from=builder /build/migrations /app/migrations

# Change ownership
RUN chown -R mage:mage /app

# Switch to non-root user
USER mage

# Expose ports
EXPOSE 17171 17179 9090

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:9090/metrics || exit 1

# Run the server
ENTRYPOINT ["/app/mage-server"]
CMD ["-config", "/app/config/config.yaml"]
