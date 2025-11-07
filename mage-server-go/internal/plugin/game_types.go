package plugin

// TwoPlayerDuel represents a standard 1v1 game
type TwoPlayerDuel struct{}

func init() {
	RegisterGameType(&TwoPlayerDuel{})
}

func (g *TwoPlayerDuel) Name() string {
	return "Two Player Duel"
}

func (g *TwoPlayerDuel) MinPlayers() int {
	return 2
}

func (g *TwoPlayerDuel) MaxPlayers() int {
	return 2
}

func (g *TwoPlayerDuel) Description() string {
	return "Standard 1v1 Magic game with 20 life"
}

// FreeForAll represents a multiplayer free-for-all game
type FreeForAll struct{}

func init() {
	RegisterGameType(&FreeForAll{})
}

func (g *FreeForAll) Name() string {
	return "Free For All"
}

func (g *FreeForAll) MinPlayers() int {
	return 2
}

func (g *FreeForAll) MaxPlayers() int {
	return 10
}

func (g *FreeForAll) Description() string {
	return "Multiplayer free-for-all game"
}

// CommanderFreeForAll represents a Commander format multiplayer game
type CommanderFreeForAll struct{}

func init() {
	RegisterGameType(&CommanderFreeForAll{})
}

func (g *CommanderFreeForAll) Name() string {
	return "Commander Free For All"
}

func (g *CommanderFreeForAll) MinPlayers() int {
	return 2
}

func (g *CommanderFreeForAll) MaxPlayers() int {
	return 10
}

func (g *CommanderFreeForAll) Description() string {
	return "Commander format multiplayer game with 40 life"
}

// CommanderDuel represents a 1v1 Commander game
type CommanderDuel struct{}

func init() {
	RegisterGameType(&CommanderDuel{})
}

func (g *CommanderDuel) Name() string {
	return "Commander Duel"
}

func (g *CommanderDuel) MinPlayers() int {
	return 2
}

func (g *CommanderDuel) MaxPlayers() int {
	return 2
}

func (g *CommanderDuel) Description() string {
	return "1v1 Commander game"
}

// Brawl represents Brawl format
type Brawl struct{}

func init() {
	RegisterGameType(&Brawl{})
}

func (g *Brawl) Name() string {
	return "Brawl"
}

func (g *Brawl) MinPlayers() int {
	return 2
}

func (g *Brawl) MaxPlayers() int {
	return 10
}

func (g *Brawl) Description() string {
	return "Brawl format game"
}

// CanadianHighlander represents Canadian Highlander format
type CanadianHighlander struct{}

func init() {
	RegisterGameType(&CanadianHighlander{})
}

func (g *CanadianHighlander) Name() string {
	return "Canadian Highlander"
}

func (g *CanadianHighlander) MinPlayers() int {
	return 2
}

func (g *CanadianHighlander) MaxPlayers() int {
	return 10
}

func (g *CanadianHighlander) Description() string {
	return "Canadian Highlander singleton format"
}

// Add more game types as needed:
// - Momir Basic
// - Oathbreaker
// - Penny Dreadful Commander
// - Tiny Leaders
// - etc.
