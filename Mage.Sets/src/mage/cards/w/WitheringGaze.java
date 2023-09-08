
package mage.cards.w;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox

 */
public final class WitheringGaze extends CardImpl {

    public WitheringGaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Target opponent reveals their hand. You draw a card for each Forest and green card in it.
        this.getSpellAbility().addEffect(new RevealHandTargetEffect());
        this.getSpellAbility().addEffect(new WitheringGazeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private WitheringGaze(final WitheringGaze card) {
        super(card);
    }

    @Override
    public WitheringGaze copy() {
        return new WitheringGaze(this);
    }
}

class WitheringGazeEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Forest or green card");

    static {
        filter.add(Predicates.or(SubType.FOREST.getPredicate(),
            new ColorPredicate(ObjectColor.GREEN)));
    }

    public WitheringGazeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "You draw a card for each Forest and green card in it";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
        if(controller != null && targetPlayer != null) {
            int count = 0;
            for(Card card : targetPlayer.getHand().getCards(game)) {
               if(filter.match(card, game)) {
                   count++;
               }
            }
            controller.drawCards(count, source, game);
            return true;
        }
        return false;
    }

    private WitheringGazeEffect(final WitheringGazeEffect effect) {
        super(effect);
    }

    @Override
    public WitheringGazeEffect copy() {
        return new WitheringGazeEffect(this);
    }
}
