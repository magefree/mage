package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolarBlaze extends CardImpl {

    public SolarBlaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{W}");

        // Each creature deals damage to itself equal to its power.
        this.getSpellAbility().addEffect(new SolarBlazeEffect());
    }

    private SolarBlaze(final SolarBlaze card) {
        super(card);
    }

    @Override
    public SolarBlaze copy() {
        return new SolarBlaze(this);
    }
}

class SolarBlazeEffect extends OneShotEffect {

    SolarBlazeEffect() {
        super(Outcome.Benefit);
        staticText = "Each creature deals damage to itself equal to its power.";
    }

    private SolarBlazeEffect(final SolarBlazeEffect effect) {
        super(effect);
    }

    @Override
    public SolarBlazeEffect copy() {
        return new SolarBlazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)
        ) {
            permanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
        }
        return true;
    }
}