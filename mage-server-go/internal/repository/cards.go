package repository

import (
	"context"
	"fmt"
	"sync"
	"time"

	"go.uber.org/zap"
)

// Card represents a Magic card
type Card struct {
	ID            int64
	CardNumber    string
	SetCode       string
	Name          string
	CardType      string
	ManaCost      string
	Power         string
	Toughness     string
	RulesText     string
	FlavorText    string
	OriginalText  string
	OriginalType  string
	CN            int64
	CardName      string
	Rarity        string
	CardClassName string
	CreatedAt     time.Time
}

// CardRepository handles card database operations
type CardRepository struct {
	db     *DB
	cache  *cardCache
	logger *zap.Logger
}

// NewCardRepository creates a new card repository
func NewCardRepository(db *DB, logger *zap.Logger) *CardRepository {
	return &CardRepository{
		db:     db,
		cache:  newCardCache(10000), // Cache up to 10k cards
		logger: logger,
	}
}

// GetByID retrieves a card by ID
func (r *CardRepository) GetByID(ctx context.Context, id int64) (*Card, error) {
	// Check cache first
	if card, ok := r.cache.get(fmt.Sprintf("id:%d", id)); ok {
		return card, nil
	}

	query := `
		SELECT id, card_number, set_code, name, card_type, mana_cost,
		       power, toughness, rules_text, flavor_text, original_text,
		       original_type, cn, card_name, rarity, card_class_name, created_at
		FROM cards
		WHERE id = $1
	`

	card := &Card{}
	err := r.db.Pool.QueryRow(ctx, query, id).Scan(
		&card.ID, &card.CardNumber, &card.SetCode, &card.Name, &card.CardType,
		&card.ManaCost, &card.Power, &card.Toughness, &card.RulesText,
		&card.FlavorText, &card.OriginalText, &card.OriginalType, &card.CN,
		&card.CardName, &card.Rarity, &card.CardClassName, &card.CreatedAt,
	)

	if err != nil {
		return nil, fmt.Errorf("failed to get card: %w", err)
	}

	// Cache the result
	r.cache.set(fmt.Sprintf("id:%d", id), card)

	return card, nil
}

// GetByName retrieves cards by name
func (r *CardRepository) GetByName(ctx context.Context, name string) ([]*Card, error) {
	query := `
		SELECT id, card_number, set_code, name, card_type, mana_cost,
		       power, toughness, rules_text, flavor_text, original_text,
		       original_type, cn, card_name, rarity, card_class_name, created_at
		FROM cards
		WHERE name = $1
		ORDER BY set_code, card_number
	`

	rows, err := r.db.Pool.Query(ctx, query, name)
	if err != nil {
		return nil, fmt.Errorf("failed to query cards: %w", err)
	}
	defer rows.Close()

	cards := make([]*Card, 0)
	for rows.Next() {
		card := &Card{}
		err := rows.Scan(
			&card.ID, &card.CardNumber, &card.SetCode, &card.Name, &card.CardType,
			&card.ManaCost, &card.Power, &card.Toughness, &card.RulesText,
			&card.FlavorText, &card.OriginalText, &card.OriginalType, &card.CN,
			&card.CardName, &card.Rarity, &card.CardClassName, &card.CreatedAt,
		)
		if err != nil {
			return nil, fmt.Errorf("failed to scan card: %w", err)
		}
		cards = append(cards, card)
	}

	return cards, nil
}

// SearchByName performs a full-text search on card names
func (r *CardRepository) SearchByName(ctx context.Context, searchTerm string, limit int) ([]*Card, error) {
	query := `
		SELECT id, card_number, set_code, name, card_type, mana_cost,
		       power, toughness, rules_text, flavor_text, original_text,
		       original_type, cn, card_name, rarity, card_class_name, created_at
		FROM cards
		WHERE name ILIKE $1
		ORDER BY name
		LIMIT $2
	`

	rows, err := r.db.Pool.Query(ctx, query, "%"+searchTerm+"%", limit)
	if err != nil {
		return nil, fmt.Errorf("failed to search cards: %w", err)
	}
	defer rows.Close()

	cards := make([]*Card, 0)
	for rows.Next() {
		card := &Card{}
		err := rows.Scan(
			&card.ID, &card.CardNumber, &card.SetCode, &card.Name, &card.CardType,
			&card.ManaCost, &card.Power, &card.Toughness, &card.RulesText,
			&card.FlavorText, &card.OriginalText, &card.OriginalType, &card.CN,
			&card.CardName, &card.Rarity, &card.CardClassName, &card.CreatedAt,
		)
		if err != nil {
			return nil, fmt.Errorf("failed to scan card: %w", err)
		}
		cards = append(cards, card)
	}

	return cards, nil
}

// GetBySetCode retrieves all cards from a set
func (r *CardRepository) GetBySetCode(ctx context.Context, setCode string) ([]*Card, error) {
	query := `
		SELECT id, card_number, set_code, name, card_type, mana_cost,
		       power, toughness, rules_text, flavor_text, original_text,
		       original_type, cn, card_name, rarity, card_class_name, created_at
		FROM cards
		WHERE set_code = $1
		ORDER BY card_number
	`

	rows, err := r.db.Pool.Query(ctx, query, setCode)
	if err != nil {
		return nil, fmt.Errorf("failed to query cards: %w", err)
	}
	defer rows.Close()

	cards := make([]*Card, 0)
	for rows.Next() {
		card := &Card{}
		err := rows.Scan(
			&card.ID, &card.CardNumber, &card.SetCode, &card.Name, &card.CardType,
			&card.ManaCost, &card.Power, &card.Toughness, &card.RulesText,
			&card.FlavorText, &card.OriginalText, &card.OriginalType, &card.CN,
			&card.CardName, &card.Rarity, &card.CardClassName, &card.CreatedAt,
		)
		if err != nil {
			return nil, fmt.Errorf("failed to scan card: %w", err)
		}
		cards = append(cards, card)
	}

	return cards, nil
}

// Create creates a new card
func (r *CardRepository) Create(ctx context.Context, card *Card) error {
	query := `
		INSERT INTO cards (card_number, set_code, name, card_type, mana_cost,
		                   power, toughness, rules_text, flavor_text, original_text,
		                   original_type, cn, card_name, rarity, card_class_name)
		VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15)
		RETURNING id, created_at
	`

	err := r.db.Pool.QueryRow(ctx, query,
		card.CardNumber, card.SetCode, card.Name, card.CardType, card.ManaCost,
		card.Power, card.Toughness, card.RulesText, card.FlavorText, card.OriginalText,
		card.OriginalType, card.CN, card.CardName, card.Rarity, card.CardClassName,
	).Scan(&card.ID, &card.CreatedAt)

	if err != nil {
		return fmt.Errorf("failed to create card: %w", err)
	}

	return nil
}

// cardCache is a simple in-memory cache for cards
type cardCache struct {
	items   map[string]*cacheItem
	maxSize int
	mu      sync.RWMutex
}

type cacheItem struct {
	card      *Card
	expiresAt time.Time
}

func newCardCache(maxSize int) *cardCache {
	return &cardCache{
		items:   make(map[string]*cacheItem),
		maxSize: maxSize,
	}
}

func (c *cardCache) get(key string) (*Card, bool) {
	c.mu.RLock()
	defer c.mu.RUnlock()

	item, ok := c.items[key]
	if !ok {
		return nil, false
	}

	if time.Now().After(item.expiresAt) {
		return nil, false
	}

	return item.card, true
}

func (c *cardCache) set(key string, card *Card) {
	c.mu.Lock()
	defer c.mu.Unlock()

	// Simple eviction: remove oldest items if cache is full
	if len(c.items) >= c.maxSize {
		// Remove first item (simple FIFO)
		for k := range c.items {
			delete(c.items, k)
			break
		}
	}

	c.items[key] = &cacheItem{
		card:      card,
		expiresAt: time.Now().Add(24 * time.Hour),
	}
}

func (c *cardCache) clear() {
	c.mu.Lock()
	defer c.mu.Unlock()
	c.items = make(map[string]*cacheItem)
}
