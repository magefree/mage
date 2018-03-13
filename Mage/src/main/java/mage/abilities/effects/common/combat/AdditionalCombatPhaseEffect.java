package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;

class AdditionalCombatPhaseEffect extends OneShotEffect {

    public AdditionalCombatPhaseEffect() {
        super(Outcome.Benefit);
        staticText = "After this phase, there is an additional combat phase";
    }

    public AdditionalCombatPhaseEffect(final AdditionalCombatPhaseEffect effect) {
        super(effect);
    }

    @Override
    public AdditionalCombatPhaseEffect copy() {
        return new AdditionalCombatPhaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.COMBAT, null, false));
        return true;
    }
}
