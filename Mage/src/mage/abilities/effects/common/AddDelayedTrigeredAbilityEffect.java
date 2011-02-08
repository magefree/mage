package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class AddDelayedTrigeredAbilityEffect extends OneShotEffect<AddDelayedTrigeredAbilityEffect> {
    private DelayedTriggeredAbility ability;

    public AddDelayedTrigeredAbilityEffect(DelayedTriggeredAbility ability) {
        super(Constants.Outcome.AddAbility);
        this.ability = ability;
    }

    public AddDelayedTrigeredAbilityEffect(final AddDelayedTrigeredAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ability.setSourceId(source.getSourceId());
		ability.setControllerId(source.getControllerId());
		game.addDelayedTriggeredAbility(ability);
        return true;
    }

    @Override
    public AddDelayedTrigeredAbilityEffect copy() {
        return new AddDelayedTrigeredAbilityEffect(this);
    }
}
