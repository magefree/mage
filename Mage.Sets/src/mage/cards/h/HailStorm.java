package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author L_J
 */
public final class HailStorm extends CardImpl {

    public HailStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}");

        // Hail Storm deals 2 damage to each attacking creature and 1 damage to you and each creature you control.
        this.getSpellAbility().addEffect(new HailStormEffect());
    }

    private HailStorm(final HailStorm card) {
        super(card);
    }

    @Override
    public HailStorm copy() {
        return new HailStorm(this);
    }
}

// needed for simultaneous damage
class HailStormEffect extends OneShotEffect {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature();

    HailStormEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 2 damage to each attacking creature " +
                "and 1 damage to you and each creature you control";
    }

    private HailStormEffect(final HailStormEffect effect) {
        super(effect);
    }

    @Override
    public HailStormEffect copy() {
        return new HailStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new DamageAllEffect(2, filter).apply(game, source);
        new DamageControllerEffect(1).apply(game, source);
        new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED).apply(game, source);
        return true;
    }
}
