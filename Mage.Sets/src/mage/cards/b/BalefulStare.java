
package mage.cards.b;

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
public final class BalefulStare extends CardImpl {

    public BalefulStare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Target opponent reveals their hand. You draw a card for each Mountain and red card in it.
        this.getSpellAbility().addEffect(new RevealHandTargetEffect());
        this.getSpellAbility().addEffect(new BalefulStareEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private BalefulStare(final BalefulStare card) {
        super(card);
    }

    @Override
    public BalefulStare copy() {
        return new BalefulStare(this);
    }
}

class BalefulStareEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Mountain or red card");

    static {
        filter.add(Predicates.or(SubType.MOUNTAIN.getPredicate(),
            new ColorPredicate(ObjectColor.RED)));
    }

    public BalefulStareEffect() {
        super(Outcome.DrawCard);
        this.staticText = "You draw a card for each Mountain and red card in it";
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

    private BalefulStareEffect(final BalefulStareEffect effect) {
        super(effect);
    }

    @Override
    public BalefulStareEffect copy() {
        return new BalefulStareEffect(this);
    }
}
