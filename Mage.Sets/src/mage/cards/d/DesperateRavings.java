
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author North
 */
public final class DesperateRavings extends CardImpl {

    public DesperateRavings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");


        // Draw two cards, then discard a card at random.
        this.getSpellAbility().addEffect(new DesperateRavingsEffect());
        // Flashback {2}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{U}")));
    }

    private DesperateRavings(final DesperateRavings card) {
        super(card);
    }

    @Override
    public DesperateRavings copy() {
        return new DesperateRavings(this);
    }
}

class DesperateRavingsEffect extends OneShotEffect {

    public DesperateRavingsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw two cards, then discard a card at random";
    }

    public DesperateRavingsEffect(final DesperateRavingsEffect effect) {
        super(effect);
    }

    @Override
    public DesperateRavingsEffect copy() {
        return new DesperateRavingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(2, source, game);
            Cards hand = player.getHand();
            Card card = hand.getRandom(game);
            player.discard(card, false, source, game);
            return true;
        }
        return false;
    }
}
