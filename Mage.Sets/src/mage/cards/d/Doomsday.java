
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

    public Doomsday(final Doomsday card) {
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
        Player player = game.getPlayer(source.getControllerId());

        if (player != null) {
            //Search your library and graveyard for five cards
            Cards allCards = new CardsImpl();
            Cards cards = new CardsImpl();
            allCards.addAll(player.getLibrary().getCardList());
            allCards.addAll(player.getGraveyard());
            int number = Math.min(5, allCards.size());
            TargetCard target = new TargetCard(number, number, Zone.ALL, new FilterCard());

            if (player.choose(Outcome.Benefit, allCards, target, game)) {
                // exile the rest
                for (UUID uuid : allCards) {
                    if (!target.getTargets().contains(uuid)) {
                        Card card = game.getCard(uuid);
                        if (card != null) {
                            card.moveToExile(null, "Doomsday", source.getSourceId(), game);
                        }
                    } else {
                        cards.add(uuid);
                    }

                }
                //Put the chosen cards on top of your library in any order
                target = new TargetCard(Zone.ALL, new FilterCard("Card to put on top"));
                while (cards.size() > 1 && player.canRespond()) {
                    player.choose(Outcome.Neutral, cards, target, game);
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                    target.clearChosen();
                }
                if (cards.size() == 1) {
                    Card card = cards.get(cards.iterator().next(), game);
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
            }

            return true;
        }
        return false;
    }
}
