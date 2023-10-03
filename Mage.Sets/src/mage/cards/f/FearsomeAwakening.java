
package mage.cards.f;

import java.util.UUID;
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

/**
 *
 * @author fireshoes
 */
public final class FearsomeAwakening extends CardImpl {

    public FearsomeAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature card from your graveyard to the battlefield. If it's a Dragon, put two +1/+1 counters on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new FearsomeAwakeningEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private FearsomeAwakening(final FearsomeAwakening card) {
        super(card);
    }

    @Override
    public FearsomeAwakening copy() {
        return new FearsomeAwakening(this);
    }
}

class FearsomeAwakeningEffect extends OneShotEffect {

    public FearsomeAwakeningEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If it's a Dragon, put two +1/+1 counters on it";
    }

    private FearsomeAwakeningEffect(final FearsomeAwakeningEffect effect) {
        super(effect);
    }

    @Override
    public FearsomeAwakeningEffect copy() {
        return new FearsomeAwakeningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.hasSubtype(SubType.DRAGON, game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }
}
