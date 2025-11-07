package rating

import (
	"math"
)

// Glicko-2 rating system constants
const (
	DefaultRating          = 1500.0
	DefaultDeviation       = 350.0
	DefaultVolatility      = 0.06
	Tau                    = 0.5 // System constant (volatility change)
	ConvergenceTolerance   = 0.000001
)

// Player represents a player's rating data
type Player struct {
	Rating     float64 // Glicko-2 rating (μ)
	Deviation  float64 // Rating deviation (φ)
	Volatility float64 // Rating volatility (σ)
}

// NewPlayer creates a new player with default ratings
func NewPlayer() *Player {
	return &Player{
		Rating:     DefaultRating,
		Deviation:  DefaultDeviation,
		Volatility: DefaultVolatility,
	}
}

// MatchResult represents the result of a match
type MatchResult struct {
	OpponentRating    float64
	OpponentDeviation float64
	Score             float64 // 1.0 for win, 0.5 for draw, 0.0 for loss
}

// Calculator performs Glicko-2 rating calculations
type Calculator struct {
	tau float64
}

// NewCalculator creates a new rating calculator
func NewCalculator() *Calculator {
	return &Calculator{
		tau: Tau,
	}
}

// UpdateRating updates a player's rating based on match results
func (c *Calculator) UpdateRating(player *Player, results []MatchResult) *Player {
	if len(results) == 0 {
		// No games played, only update deviation
		return c.updateUnplayedPeriod(player)
	}

	// Step 2: Convert to Glicko-2 scale
	mu := (player.Rating - 1500) / 173.7178
	phi := player.Deviation / 173.7178
	sigma := player.Volatility

	// Step 3: Compute v (variance estimate)
	v := c.computeVariance(mu, results)

	// Step 4: Compute delta (performance estimate)
	delta := c.computeDelta(mu, v, results)

	// Step 5: Update volatility
	newSigma := c.updateVolatility(sigma, phi, v, delta)

	// Step 6: Update deviation
	phiStar := math.Sqrt(phi*phi + newSigma*newSigma)

	// Step 7: Update rating and deviation
	newPhi := 1.0 / math.Sqrt(1.0/(phiStar*phiStar)+1.0/v)
	newMu := mu + newPhi*newPhi*c.computeOpponentSum(mu, results)

	// Step 8: Convert back to original scale
	return &Player{
		Rating:     newMu*173.7178 + 1500,
		Deviation:  newPhi * 173.7178,
		Volatility: newSigma,
	}
}

// updateUnplayedPeriod updates a player's rating when they haven't played
func (c *Calculator) updateUnplayedPeriod(player *Player) *Player {
	phi := player.Deviation / 173.7178
	sigma := player.Volatility

	// Only deviation increases
	newPhi := math.Sqrt(phi*phi + sigma*sigma)

	return &Player{
		Rating:     player.Rating,
		Deviation:  newPhi * 173.7178,
		Volatility: player.Volatility,
	}
}

// g function: reduces impact of games against high-deviation opponents
func (c *Calculator) g(deviation float64) float64 {
	return 1.0 / math.Sqrt(1.0+3.0*deviation*deviation/(math.Pi*math.Pi))
}

// E function: expected score against an opponent
func (c *Calculator) E(mu, opponentMu, opponentPhi float64) float64 {
	return 1.0 / (1.0 + math.Exp(-c.g(opponentPhi)*(mu-opponentMu)))
}

// computeVariance computes the variance estimate
func (c *Calculator) computeVariance(mu float64, results []MatchResult) float64 {
	sum := 0.0
	for _, result := range results {
		opponentMu := (result.OpponentRating - 1500) / 173.7178
		opponentPhi := result.OpponentDeviation / 173.7178

		gPhi := c.g(opponentPhi)
		e := c.E(mu, opponentMu, opponentPhi)

		sum += gPhi * gPhi * e * (1.0 - e)
	}
	return 1.0 / sum
}

// computeDelta computes the performance estimate
func (c *Calculator) computeDelta(mu, v float64, results []MatchResult) float64 {
	return v * c.computeOpponentSum(mu, results)
}

// computeOpponentSum computes the sum over opponents for rating update
func (c *Calculator) computeOpponentSum(mu float64, results []MatchResult) float64 {
	sum := 0.0
	for _, result := range results {
		opponentMu := (result.OpponentRating - 1500) / 173.7178
		opponentPhi := result.OpponentDeviation / 173.7178

		gPhi := c.g(opponentPhi)
		e := c.E(mu, opponentMu, opponentPhi)

		sum += gPhi * (result.Score - e)
	}
	return sum
}

// updateVolatility updates the volatility using the iterative algorithm
func (c *Calculator) updateVolatility(sigma, phi, v, delta float64) float64 {
	a := math.Log(sigma * sigma)
	deltaSq := delta * delta
	phiSq := phi * phi
	vSq := v * v

	// Define f(x) function
	f := func(x float64) float64 {
		expX := math.Exp(x)
		phiSqPlusExpX := phiSq + vSq + expX
		term1 := (expX * (deltaSq - phiSq - vSq - expX)) / (2.0 * phiSqPlusExpX * phiSqPlusExpX)
		term2 := (x - a) / (c.tau * c.tau)
		return term1 - term2
	}

	// Illinois algorithm to find zero of f
	A := a
	B := 0.0
	if deltaSq > phiSq+vSq {
		B = math.Log(deltaSq - phiSq - vSq)
	} else {
		k := 1.0
		for f(a-k*c.tau) < 0 {
			k++
		}
		B = a - k*c.tau
	}

	fA := f(A)
	fB := f(B)

	// Iterate to find zero
	for math.Abs(B-A) > ConvergenceTolerance {
		C := A + (A-B)*fA/(fB-fA)
		fC := f(C)

		if fC*fB < 0 {
			A = B
			fA = fB
		} else {
			fA = fA / 2.0
		}

		B = C
		fB = fC
	}

	return math.Exp(A / 2.0)
}

// CalculateMatchRating calculates new rating after a single match
func (c *Calculator) CalculateMatchRating(playerRating, playerDeviation, playerVolatility float64,
	opponentRating, opponentDeviation float64, score float64) (newRating, newDeviation, newVolatility float64) {

	player := &Player{
		Rating:     playerRating,
		Deviation:  playerDeviation,
		Volatility: playerVolatility,
	}

	results := []MatchResult{
		{
			OpponentRating:    opponentRating,
			OpponentDeviation: opponentDeviation,
			Score:             score,
		},
	}

	updated := c.UpdateRating(player, results)
	return updated.Rating, updated.Deviation, updated.Volatility
}
