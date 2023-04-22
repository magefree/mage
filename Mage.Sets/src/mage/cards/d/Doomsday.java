
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseHalfLifeEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Plopman
 */
public final class Doomsday extends CardImpl {

    public Doomsday(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}{B}");

        // Search your library and graveyard for five cards and exile the rest. Put the chosen cards on top of your library in any order.
        this.getSpellAbility().addEffect(new DoomsdayEffect());

        // You lose half your life, rounded up.
        this.getSpellAbility().addEffect(new LoseHalfLifeEffect());
    }

    private Doomsday(final Doomsday card) {
        super(card);
    }

    @Override
    public Doomsday copy() {
        return new Doomsday(this);
    }
}

class DoomsdayEffect extends OneShotEffect {

    public DoomsdayEffect() {
        super(Outcome.LoseLife);
        staticText = "Search your library and graveyard for five cards and exile the rest. Put the chosen cards on top of your library in any order";
    }

    public DoomsdayEffect(final DoomsdayEffect effect) {
        super(effect);
    }

    @Override
    public DoomsdayEffect copy() {
        return new DoomsdayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            //Search your library and graveyard for five cards
            Cards allCards = new CardsImpl();
            allCards.addAll(controller.getLibrary().getCardList());
            allCards.addAll(controller.getGraveyard());
            int number = Math.min(5, allCards.size());
            TargetCard target = new TargetCard(number, number, Zone.ALL, new FilterCard());

            if (controller.choose(Outcome.Benefit, allCards, target, source, game)) {
                Cards toLibrary = new CardsImpl(target.getTargets());
                allCards.removeAll(toLibrary);
                // Exile the rest
                controller.moveCards(allCards, Zone.EXILED, source, game);
                //Put the chosen cards on top of your library in any order
                controller.putCardsOnTopOfLibrary(toLibrary, game, source, true);
            }
            return true;
        }
        return false;
    }
}
