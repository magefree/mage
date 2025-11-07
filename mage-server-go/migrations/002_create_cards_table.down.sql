-- Drop indexes
DROP INDEX IF EXISTS idx_cards_set_name;
DROP INDEX IF EXISTS idx_cards_rules_text_trgm;
DROP INDEX IF EXISTS idx_cards_name_trgm;
DROP INDEX IF EXISTS idx_cards_card_number;
DROP INDEX IF EXISTS idx_cards_cn;
DROP INDEX IF EXISTS idx_cards_set_code;
DROP INDEX IF EXISTS idx_cards_name;

-- Drop table
DROP TABLE IF EXISTS cards;

-- Note: We don't drop the pg_trgm extension as it might be used by other tables
