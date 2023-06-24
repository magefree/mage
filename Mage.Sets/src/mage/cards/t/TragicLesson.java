
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class TragicLesson extends CardImpl {

    public TragicLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Draw two cards. Then discard a card unless you return a land you control to its owner's hand.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new TragicLessonEffect());
    }

    private TragicLesson(final TragicLesson card) {
        super(card);
    }

    @Override
    public TragicLesson copy() {
        return new TragicLesson(this);
    }
}

class TragicLessonEffect extends OneShotEffect {

    public TragicLessonEffect() {
        super(Outcome.Discard);
        staticText = "Then discard a card unless you return a land you control to its owner's hand.";
    }

    public TragicLessonEffect(final TragicLessonEffect effect) {
        super(effect);
    }

    @Override
    public TragicLessonEffect copy() {
        return new TragicLessonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null
                && controller.chooseUse(Outcome.Discard, "Return a land you control to its owner's hand?", source, game)) {
            Cost cost = new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_A_LAND));
            if (cost.canPay(source, source, controller.getId(), game)) {
                if (cost.pay(source, game, source, controller.getId(), false, null)) {
                    return true;                    
                }
            }
        }
        if (controller != null) {
            controller.discard(1, false, false, source, game);
            return true;
        }
        return false;
    }
}
