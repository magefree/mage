#!/bin/bash

set -e

PROTO_DIR="api/proto"
OUT_DIR="pkg/proto"

echo "Generating protobuf files from ${PROTO_DIR}..."

# Create output directory if it doesn't exist
mkdir -p ${OUT_DIR}

# Find all .proto files and generate Go code
find ${PROTO_DIR} -name "*.proto" | while read proto_file; do
    echo "Generating: ${proto_file}"
    protoc \
        --go_out=${OUT_DIR} \
        --go_opt=paths=source_relative \
        --go-grpc_out=${OUT_DIR} \
        --go-grpc_opt=paths=source_relative \
        --proto_path=${PROTO_DIR} \
        ${proto_file}
done

echo "Protobuf generation complete!"
