-- Create authorized_users table
CREATE TABLE IF NOT EXISTS authorized_users (
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

CREATE INDEX IF NOT EXISTS idx_authorized_users_name ON authorized_users(name);
CREATE INDEX IF NOT EXISTS idx_authorized_users_email ON authorized_users(email);

-- Trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_authorized_users_updated_at BEFORE UPDATE
    ON authorized_users FOR EACH ROW
    EXECUTE PROCEDURE update_updated_at_column();
