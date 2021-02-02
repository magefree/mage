
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
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
 * @author Quercitron
 */
public final class DarkBargain extends CardImpl {

    public DarkBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Look at the top three cards of your library. Put two of them into your hand and the rest into your graveyard.
        // Dark Bargain deals 2 damage to you.
        this.getSpellAbility().addEffect(new DarkBargainEffect());
        this.getSpellAbility().addEffect(new DamageControllerEffect(2));
    }

    private DarkBargain(final DarkBargain card) {
        super(card);
    }

    @Override
    public DarkBargain copy() {
        return new DarkBargain(this);
    }
}

class DarkBargainEffect extends OneShotEffect {

    public DarkBargainEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top three cards of your library. Put two of them into your hand and the other into your graveyard";
    }

    public DarkBargainEffect(final DarkBargainEffect effect) {
        super(effect);
    }

    @Override
    public DarkBargainEffect copy() {
        return new DarkBargainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceOject = source.getSourceObject(game);
        if (player != null && sourceOject != null) {
            Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                Cards cardsToHand = new CardsImpl();
                player.lookAtCards(sourceOject.getIdName(), cards, game);
                TargetCard target = new TargetCard(Math.min(2, cards.size()), Zone.LIBRARY, new FilterCard("two cards to put in your hand"));
                if (player.choose(Outcome.DrawCard, cards, target, game)) {
                    for (UUID targetId : target.getTargets()) {
                        Card card = cards.get(targetId, game);
                        if (card != null) {
                            cardsToHand.add(card);
                            cards.remove(card);
                        }
                    }
                }
                player.moveCards(cardsToHand, Zone.HAND, source, game);
                player.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
