package plugin

import (
	"fmt"
	"sync"
)

// GameType represents a game type (e.g., Two Player Duel, Commander, etc.)
type GameType interface {
	Name() string
	MinPlayers() int
	MaxPlayers() int
	Description() string
}

// TournamentType represents a tournament type (e.g., Constructed, Draft, Sealed)
type TournamentType interface {
	Name() string
	Description() string
	SupportsBoosterDraft() bool
}

// PlayerType represents a player type (e.g., Human, AI)
type PlayerType interface {
	Name() string
	IsAI() bool
	Description() string
}

// Registry manages game types, tournament types, and player types
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

// RegisterGameType registers a game type
func RegisterGameType(gt GameType) {
	defaultRegistry.mu.Lock()
	defer defaultRegistry.mu.Unlock()
	defaultRegistry.gameTypes[gt.Name()] = gt
}

// RegisterTournamentType registers a tournament type
func RegisterTournamentType(tt TournamentType) {
	defaultRegistry.mu.Lock()
	defer defaultRegistry.mu.Unlock()
	defaultRegistry.tournamentTypes[tt.Name()] = tt
}

// RegisterPlayerType registers a player type
func RegisterPlayerType(pt PlayerType) {
	defaultRegistry.mu.Lock()
	defer defaultRegistry.mu.Unlock()
	defaultRegistry.playerTypes[pt.Name()] = pt
}

// GetGameType retrieves a game type by name
func GetGameType(name string) (GameType, error) {
	defaultRegistry.mu.RLock()
	defer defaultRegistry.mu.RUnlock()

	gt, ok := defaultRegistry.gameTypes[name]
	if !ok {
		return nil, fmt.Errorf("game type not found: %s", name)
	}
	return gt, nil
}

// GetTournamentType retrieves a tournament type by name
func GetTournamentType(name string) (TournamentType, error) {
	defaultRegistry.mu.RLock()
	defer defaultRegistry.mu.RUnlock()

	tt, ok := defaultRegistry.tournamentTypes[name]
	if !ok {
		return nil, fmt.Errorf("tournament type not found: %s", name)
	}
	return tt, nil
}

// GetPlayerType retrieves a player type by name
func GetPlayerType(name string) (PlayerType, error) {
	defaultRegistry.mu.RLock()
	defer defaultRegistry.mu.RUnlock()

	pt, ok := defaultRegistry.playerTypes[name]
	if !ok {
		return nil, fmt.Errorf("player type not found: %s", name)
	}
	return pt, nil
}

// GetAllGameTypes returns all registered game types
func GetAllGameTypes() []GameType {
	defaultRegistry.mu.RLock()
	defer defaultRegistry.mu.RUnlock()

	types := make([]GameType, 0, len(defaultRegistry.gameTypes))
	for _, gt := range defaultRegistry.gameTypes {
		types = append(types, gt)
	}
	return types
}

// GetAllTournamentTypes returns all registered tournament types
func GetAllTournamentTypes() []TournamentType {
	defaultRegistry.mu.RLock()
	defer defaultRegistry.mu.RUnlock()

	types := make([]TournamentType, 0, len(defaultRegistry.tournamentTypes))
	for _, tt := range defaultRegistry.tournamentTypes {
		types = append(types, tt)
	}
	return types
}

// GetAllPlayerTypes returns all registered player types
func GetAllPlayerTypes() []PlayerType {
	defaultRegistry.mu.RLock()
	defer defaultRegistry.mu.RUnlock()

	types := make([]PlayerType, 0, len(defaultRegistry.playerTypes))
	for _, pt := range defaultRegistry.playerTypes {
		types = append(types, pt)
	}
	return types
}
