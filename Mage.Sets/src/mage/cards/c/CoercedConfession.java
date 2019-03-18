
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class CoercedConfession extends CardImpl {

    public CoercedConfession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U/B}");


        // Target player puts the top four cards of their library into their graveyard. You draw a card for each creature card put into a graveyard this way.
        getSpellAbility().addEffect(new CoercedConfessionMillEffect());
        getSpellAbility().addTarget(new TargetPlayer());
    }

    public CoercedConfession(final CoercedConfession card) {
        super(card);
    }

    @Override
    public CoercedConfession copy() {
        return new CoercedConfession(this);
    }
}

class CoercedConfessionMillEffect extends OneShotEffect {

    public CoercedConfessionMillEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Target player puts the top four cards of their library into their graveyard. You draw a card for each creature card put into a graveyard this way";
    }

    public CoercedConfessionMillEffect(final CoercedConfessionMillEffect effect) {
        super(effect);
    }

    @Override
    public CoercedConfessionMillEffect copy() {
        return new CoercedConfessionMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int foundCreatures = 0;
            Cards cards = new CardsImpl();
            for(Card card: player.getLibrary().getTopCards(game, 4)) {
                cards.add(card);
                if (card.isCreature()) {
                    ++foundCreatures;
                }
            }
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
            if (foundCreatures > 0) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    controller.drawCards(foundCreatures, game);
                }
            }
            return true;
        }
        return false;
    }
}
