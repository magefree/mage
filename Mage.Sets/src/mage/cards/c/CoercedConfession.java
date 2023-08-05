package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CoercedConfession extends CardImpl {

    public CoercedConfession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U/B}");

        // Target player puts the top four cards of their library into their graveyard. You draw a card for each creature card put into a graveyard this way.
        getSpellAbility().addEffect(new CoercedConfessionMillEffect());
        getSpellAbility().addTarget(new TargetPlayer());
    }

    private CoercedConfession(final CoercedConfession card) {
        super(card);
    }

    @Override
    public CoercedConfession copy() {
        return new CoercedConfession(this);
    }
}

class CoercedConfessionMillEffect extends OneShotEffect {

    CoercedConfessionMillEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Target player mills four cards. You draw a card for each creature card put into their graveyard this way";
    }

    private CoercedConfessionMillEffect(final CoercedConfessionMillEffect effect) {
        super(effect);
    }

    @Override
    public CoercedConfessionMillEffect copy() {
        return new CoercedConfessionMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        int creaturesMilled = player
                .millCards(4, source, game)
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> game.getState().getZone(card.getId()) == Zone.GRAVEYARD)
                .filter(card1 -> card1.isCreature(game))
                .mapToInt(x -> 1)
                .sum();
        if (creaturesMilled < 1) {
            return true;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return true;
        }
        game.getState().processAction(game);
        controller.drawCards(creaturesMilled, source, game);
        return true;
    }
}
