-- Create table_records table for storing completed game/tournament data
CREATE TABLE IF NOT EXISTS table_records (
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

-- Create indexes for efficient queries
CREATE INDEX IF NOT EXISTS idx_table_records_table_id ON table_records(table_id);
CREATE INDEX IF NOT EXISTS idx_table_records_start_time ON table_records(start_time DESC);
CREATE INDEX IF NOT EXISTS idx_table_records_game_type ON table_records(game_type);
CREATE INDEX IF NOT EXISTS idx_table_records_tournament_type ON table_records(tournament_type);

-- Create index for recent records queries
CREATE INDEX IF NOT EXISTS idx_table_records_created_at ON table_records(created_at DESC);
