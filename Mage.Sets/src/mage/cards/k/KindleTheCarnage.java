package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class KindleTheCarnage extends CardImpl {

    public KindleTheCarnage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Discard a card at random. If you do, Kindle the Carnage deals damage equal to that card's converted mana cost to each creature. You may repeat this process any number of times.
        this.getSpellAbility().addEffect(new KindleTheCarnageEffect());

    }

    private KindleTheCarnage(final KindleTheCarnage card) {
        super(card);
    }

    @Override
    public KindleTheCarnage copy() {
        return new KindleTheCarnage(this);
    }
}

class KindleTheCarnageEffect extends OneShotEffect {

    public KindleTheCarnageEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Discard a card at random. If you do, {this} deals damage equal to that card's mana value to each creature. You may repeat this process any number of times";
    }

    private KindleTheCarnageEffect(final KindleTheCarnageEffect effect) {
        super(effect);
    }

    @Override
    public KindleTheCarnageEffect copy() {
        return new KindleTheCarnageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards hand = controller.getHand();
            while (hand != null
                    && hand.size() > 0
                    && controller.chooseUse(Outcome.AIDontUseIt, "Discard a card randomly from your hand?", source, game)) {
                Card discardedCard = controller.discardOne(true, false, source, game);
                if (discardedCard != null) {
                    new DamageAllEffect(discardedCard.getManaValue(), new FilterCreaturePermanent()).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
