package plugin

// Constructed represents a constructed tournament
type Constructed struct{}

func init() {
	RegisterTournamentType(&Constructed{})
}

func (t *Constructed) Name() string {
	return "Constructed"
}

func (t *Constructed) Description() string {
	return "Constructed format tournament"
}

func (t *Constructed) SupportsBoosterDraft() bool {
	return false
}

// BoosterDraft represents a booster draft tournament
type BoosterDraft struct{}

func init() {
	RegisterTournamentType(&BoosterDraft{})
}

func (t *BoosterDraft) Name() string {
	return "Booster Draft"
}

func (t *BoosterDraft) Description() string {
	return "Booster draft tournament"
}

func (t *BoosterDraft) SupportsBoosterDraft() bool {
	return true
}

// Sealed represents a sealed tournament
type Sealed struct{}

func init() {
	RegisterTournamentType(&Sealed{})
}

func (t *Sealed) Name() string {
	return "Sealed"
}

func (t *Sealed) Description() string {
	return "Sealed deck tournament"
}

func (t *Sealed) SupportsBoosterDraft() bool {
	return false
}
