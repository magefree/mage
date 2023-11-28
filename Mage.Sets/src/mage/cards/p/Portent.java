
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class Portent extends CardImpl {

    public Portent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Look at the top three cards of target player's library, then put them back in any order. You may have that player shuffle their library.
        this.getSpellAbility().addEffect(new PortentEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                new DrawCardSourceControllerEffect(1)), false).concatBy("<br>"));
    }

    private Portent(final Portent card) {
        super(card);
    }

    @Override
    public Portent copy() {
        return new Portent(this);
    }
}

class PortentEffect extends OneShotEffect {

    public PortentEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of target player's library, then put them back in any order. You may have that player shuffle";
    }

    private PortentEffect(final PortentEffect effect) {
        super(effect);
    }

    @Override
    public PortentEffect copy() {
        return new PortentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        controller.lookAtCards(source, null, cards, game);
        controller.putCardsOnTopOfLibrary(cards, game, source, true);
        if (controller.chooseUse(Outcome.Neutral, "You may have that player shuffle", source, game)) {
            player.shuffleLibrary(source, game);
        }
        return true;
    }
}
