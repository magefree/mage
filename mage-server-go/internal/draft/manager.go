package draft

import (
	"fmt"
	"sync"
	"time"

	"github.com/google/uuid"
	"go.uber.org/zap"
)

// DraftState represents the state of a draft
type DraftState int

const (
	DraftStateWaiting DraftState = iota
	DraftStateStarting
	DraftStatePicking
	DraftStateFinished
)

func (s DraftState) String() string {
	switch s {
	case DraftStateWaiting:
		return "WAITING"
	case DraftStateStarting:
		return "STARTING"
	case DraftStatePicking:
		return "PICKING"
	case DraftStateFinished:
		return "FINISHED"
	default:
		return "UNKNOWN"
	}
}

// Card represents a card in a draft
type Card struct {
	ID       string
	Name     string
	SetCode  string
	Rarity   string
	ImageURL string
}

// Booster represents a booster pack
type Booster struct {
	Cards      []*Card
	PickedCard *Card
}

// DraftPlayer represents a player in a draft
type DraftPlayer struct {
	Name           string
	Picks          []*Card
	CurrentBooster *Booster
	BoosterLoaded  bool
}

// Draft represents a draft session
type Draft struct {
	ID            string
	TournamentID  string
	TableID       string
	State         DraftState
	Players       map[string]*DraftPlayer
	PlayerOrder   []string
	CurrentPick   int
	CurrentPack   int
	NumPacks      int
	PackSize      int
	PassDirection int // 1 for left, -1 for right
	CreateTime    time.Time
	StartTime     *time.Time
	EndTime       *time.Time
	mu            sync.RWMutex
}

// NewDraft creates a new draft
func NewDraft(tournamentID, tableID string, players []string, numPacks, packSize int) *Draft {
	draft := &Draft{
		ID:            uuid.New().String(),
		TournamentID:  tournamentID,
		TableID:       tableID,
		State:         DraftStateWaiting,
		Players:       make(map[string]*DraftPlayer),
		PlayerOrder:   players,
		CurrentPick:   1,
		CurrentPack:   1,
		NumPacks:      numPacks,
		PackSize:      packSize,
		PassDirection: 1,
		CreateTime:    time.Now(),
	}

	// Initialize players
	for _, playerName := range players {
		draft.Players[playerName] = &DraftPlayer{
			Name:  playerName,
			Picks: make([]*Card, 0),
		}
	}

	return draft
}

// AddPlayer adds a player to the draft
func (d *Draft) AddPlayer(playerName string) error {
	d.mu.Lock()
	defer d.mu.Unlock()

	if d.State != DraftStateWaiting {
		return fmt.Errorf("draft already started")
	}

	if _, exists := d.Players[playerName]; exists {
		return fmt.Errorf("player already in draft")
	}

	d.Players[playerName] = &DraftPlayer{
		Name:  playerName,
		Picks: make([]*Card, 0),
	}
	d.PlayerOrder = append(d.PlayerOrder, playerName)

	return nil
}

// SetState sets the draft state
func (d *Draft) SetState(state DraftState) {
	d.mu.Lock()
	defer d.mu.Unlock()

	d.State = state

	if state == DraftStatePicking && d.StartTime == nil {
		now := time.Now()
		d.StartTime = &now
	} else if state == DraftStateFinished {
		now := time.Now()
		d.EndTime = &now
	}
}

// GetState returns the current draft state
func (d *Draft) GetState() DraftState {
	d.mu.RLock()
	defer d.mu.RUnlock()
	return d.State
}

// SetCurrentBooster sets the current booster for a player
func (d *Draft) SetCurrentBooster(playerName string, cards []*Card) error {
	d.mu.Lock()
	defer d.mu.Unlock()

	player, ok := d.Players[playerName]
	if !ok {
		return fmt.Errorf("player not found")
	}

	player.CurrentBooster = &Booster{
		Cards: cards,
	}
	player.BoosterLoaded = false

	return nil
}

// PickCard records a card pick by a player
func (d *Draft) PickCard(playerName, cardID string) error {
	d.mu.Lock()
	defer d.mu.Unlock()

	player, ok := d.Players[playerName]
	if !ok {
		return fmt.Errorf("player not found")
	}

	if player.CurrentBooster == nil {
		return fmt.Errorf("no booster available")
	}

	// Find and remove the card from the booster
	var pickedCard *Card
	for i, card := range player.CurrentBooster.Cards {
		if card.ID == cardID {
			pickedCard = card
			player.CurrentBooster.Cards = append(player.CurrentBooster.Cards[:i], player.CurrentBooster.Cards[i+1:]...)
			break
		}
	}

	if pickedCard == nil {
		return fmt.Errorf("card not found in booster")
	}

	// Add to player's picks
	player.Picks = append(player.Picks, pickedCard)
	player.CurrentBooster.PickedCard = pickedCard

	return nil
}

// PassBoosters passes boosters to the next player
func (d *Draft) PassBoosters() error {
	d.mu.Lock()
	defer d.mu.Unlock()

	// Create a map to hold the new boosters for each player
	newBoosters := make(map[string]*Booster)

	// Pass boosters according to direction
	for i, playerName := range d.PlayerOrder {
		player := d.Players[playerName]

		// Calculate next player index
		nextIndex := (i + d.PassDirection + len(d.PlayerOrder)) % len(d.PlayerOrder)
		nextPlayerName := d.PlayerOrder[nextIndex]

		// Pass the booster
		newBoosters[nextPlayerName] = player.CurrentBooster
	}

	// Assign new boosters
	for playerName, booster := range newBoosters {
		d.Players[playerName].CurrentBooster = booster
		d.Players[playerName].BoosterLoaded = false
	}

	// Increment pick counter
	d.CurrentPick++

	// Check if pack is done
	if d.CurrentPick > d.PackSize {
		d.CurrentPick = 1
		d.CurrentPack++

		// Reverse direction for next pack
		d.PassDirection = -d.PassDirection

		// Check if draft is done
		if d.CurrentPack > d.NumPacks {
			d.State = DraftStateFinished
			now := time.Now()
			d.EndTime = &now
		}
	}

	return nil
}

// SetBoosterLoaded marks that a player has loaded their booster
func (d *Draft) SetBoosterLoaded(playerName string) error {
	d.mu.Lock()
	defer d.mu.Unlock()

	player, ok := d.Players[playerName]
	if !ok {
		return fmt.Errorf("player not found")
	}

	player.BoosterLoaded = true
	return nil
}

// AllBoostersLoaded checks if all players have loaded their boosters
func (d *Draft) AllBoostersLoaded() bool {
	d.mu.RLock()
	defer d.mu.RUnlock()

	for _, player := range d.Players {
		if !player.BoosterLoaded {
			return false
		}
	}
	return true
}

// GetPlayerPicks returns the picks for a player
func (d *Draft) GetPlayerPicks(playerName string) ([]*Card, error) {
	d.mu.RLock()
	defer d.mu.RUnlock()

	player, ok := d.Players[playerName]
	if !ok {
		return nil, fmt.Errorf("player not found")
	}

	return player.Picks, nil
}

// Manager manages draft sessions
type Manager struct {
	drafts map[string]*Draft
	mu     sync.RWMutex
	logger *zap.Logger
}

// NewManager creates a new draft manager
func NewManager(logger *zap.Logger) *Manager {
	return &Manager{
		drafts: make(map[string]*Draft),
		logger: logger,
	}
}

// CreateDraft creates a new draft
func (m *Manager) CreateDraft(tournamentID, tableID string, players []string, numPacks, packSize int) *Draft {
	m.mu.Lock()
	defer m.mu.Unlock()

	draft := NewDraft(tournamentID, tableID, players, numPacks, packSize)
	m.drafts[draft.ID] = draft

	m.logger.Info("draft created",
		zap.String("draft_id", draft.ID),
		zap.String("tournament_id", tournamentID),
		zap.Strings("players", players),
		zap.Int("num_packs", numPacks),
	)

	return draft
}

// GetDraft retrieves a draft by ID
func (m *Manager) GetDraft(draftID string) (*Draft, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()

	draft, ok := m.drafts[draftID]
	return draft, ok
}

// RemoveDraft removes a draft
func (m *Manager) RemoveDraft(draftID string) {
	m.mu.Lock()
	defer m.mu.Unlock()

	delete(m.drafts, draftID)

	m.logger.Info("draft removed", zap.String("draft_id", draftID))
}

// GetAllDrafts returns all drafts
func (m *Manager) GetAllDrafts() []*Draft {
	m.mu.RLock()
	defer m.mu.RUnlock()

	drafts := make([]*Draft, 0, len(m.drafts))
	for _, draft := range m.drafts {
		drafts = append(drafts, draft)
	}
	return drafts
}

// GetActiveDraftCount returns the count of active drafts
func (m *Manager) GetActiveDraftCount() int {
	m.mu.RLock()
	defer m.mu.RUnlock()

	count := 0
	for _, draft := range m.drafts {
		if draft.State != DraftStateFinished {
			count++
		}
	}
	return count
}

// GenerateBooster generates a random booster pack
// This is a placeholder - real implementation would pull from card database
func (m *Manager) GenerateBooster(setCode string, packSize int) []*Card {
	// Placeholder implementation
	cards := make([]*Card, packSize)
	for i := 0; i < packSize; i++ {
		cards[i] = &Card{
			ID:      uuid.New().String(),
			Name:    fmt.Sprintf("Card %d", i+1),
			SetCode: setCode,
			Rarity:  "Common",
		}
	}
	return cards
}
