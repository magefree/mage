
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class PlaneswalkersMirth extends CardImpl {

    public PlaneswalkersMirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // {3}{W}: Target opponent reveals a card at random from their hand. You gain life equal to that card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PlaneswalkersMirthEffect(), new ManaCostsImpl<>("{3}{W}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PlaneswalkersMirth(final PlaneswalkersMirth card) {
        super(card);
    }

    @Override
    public PlaneswalkersMirth copy() {
        return new PlaneswalkersMirth(this);
    }
}

class PlaneswalkersMirthEffect extends OneShotEffect {

    public PlaneswalkersMirthEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals a card at random from their hand. You gain life equal to that card's mana value";
    }

    public PlaneswalkersMirthEffect(final PlaneswalkersMirthEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        Player player = game.getPlayer(source.getControllerId());
        if (opponent != null && player!= null && !opponent.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();
            Card card = opponent.getHand().getRandom(game);
            if (card != null) {
                revealed.add(card);
                opponent.revealCards("Planeswalker's Mirth", revealed, game);
                player.gainLife(card.getManaValue(), game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public PlaneswalkersMirthEffect copy() {
        return new PlaneswalkersMirthEffect(this);
    }

}