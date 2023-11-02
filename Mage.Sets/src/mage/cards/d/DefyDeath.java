package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class DefyDeath extends CardImpl {

    public DefyDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Return target creature card from your graveyard to the battlefield. If it's an Angel, put two +1/+1 counters on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new DefyDeathEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private DefyDeath(final DefyDeath card) {
        super(card);
    }

    @Override
    public DefyDeath copy() {
        return new DefyDeath(this);
    }
}

class DefyDeathEffect extends OneShotEffect {

    public DefyDeathEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If it's an Angel, put two +1/+1 counters on it";
    }

    private DefyDeathEffect(final DefyDeathEffect effect) {
        super(effect);
    }

    @Override
    public DefyDeathEffect copy() {
        return new DefyDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.hasSubtype(SubType.ANGEL, game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }
}
