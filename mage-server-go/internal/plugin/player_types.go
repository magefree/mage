package plugin

// Human represents a human player
type Human struct{}

func init() {
	RegisterPlayerType(&Human{})
}

func (p *Human) Name() string {
	return "Human"
}

func (p *Human) IsAI() bool {
	return false
}

func (p *Human) Description() string {
	return "Human player"
}

// ComputerMAX represents a computer AI player
type ComputerMAX struct{}

func init() {
	RegisterPlayerType(&ComputerMAX{})
}

func (p *ComputerMAX) Name() string {
	return "Computer - MAX"
}

func (p *ComputerMAX) IsAI() bool {
	return true
}

func (p *ComputerMAX) Description() string {
	return "Computer AI player (MAX algorithm)"
}

// ComputerDraft represents a draft AI player
type ComputerDraft struct{}

func init() {
	RegisterPlayerType(&ComputerDraft{})
}

func (p *ComputerDraft) Name() string {
	return "Computer - Draft"
}

func (p *ComputerDraft) IsAI() bool {
	return true
}

func (p *ComputerDraft) Description() string {
	return "Computer AI player optimized for drafting"
}
