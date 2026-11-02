package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author Karthikeyan010
 */

public class RemoveAllCountersEnchantedEffect extends OneShotEffect {
    public RemoveAllCountersEnchantedEffect() {
        super(Outcome.Benefit);
        staticText = "remove all counters from it";
    }

    protected RemoveAllCountersEnchantedEffect(final RemoveAllCountersEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public RemoveAllCountersEnchantedEffect copy() {
        return new RemoveAllCountersEnchantedEffect(this);

    }

    @Override
    public boolean apply(Game game , Ability source) {
        return Optional
                .ofNullable((Permanent) getValue("permanentEnteredBattlefield"))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .filter(permanent -> permanent.removeAllCounters(source, game) > 0)
                .isPresent();
    }
}
