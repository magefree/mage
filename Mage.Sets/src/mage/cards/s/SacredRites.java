
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author cbt33
 */
public final class SacredRites extends CardImpl {

    public SacredRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Discard any number of cards. Creatures you control get +0/+1 until end of turn for each card discarded this way.
        this.getSpellAbility().addEffect(new SacredRitesEffect());
    }

    public SacredRites(final SacredRites card) {
        super(card);
    }

    @Override
    public SacredRites copy() {
        return new SacredRites(this);
    }
}

class SacredRitesEffect extends OneShotEffect {

    SacredRitesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Discard any number of cards. Creatures you control get +0/+1 until end of turn for each card discarded this way.";
    }

    SacredRitesEffect(final SacredRitesEffect effect) {
        super(effect);
    }

    @Override
    public SacredRitesEffect copy() {
        return new SacredRitesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterCard("cards to discard"));
            while (controller.canRespond() && !target.isChosen()) {
                target.choose(Outcome.BoostCreature, controller.getId(), source.getSourceId(), game);
            }
            int numDiscarded = 0;
            for (UUID targetId : target.getTargets()) {
                Card card = controller.getHand().get(targetId, game);
                if (controller.discard(card, source, game)) {
                    numDiscarded++;
                }
            }
            game.addEffect(new BoostControlledEffect(0, numDiscarded, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
