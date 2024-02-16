
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class ForceMastery extends CardImpl {

    public ForceMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{U}{W}");

        // At the beggining of your upkeep, reveal the top card of your library and put that card into your hand. You gain life equal to its converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ForceMasteryEffect(), TargetController.YOU, false));

    }

    private ForceMastery(final ForceMastery card) {
        super(card);
    }

    @Override
    public ForceMastery copy() {
        return new ForceMastery(this);
    }
}

class ForceMasteryEffect extends OneShotEffect {

    ForceMasteryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library and put that card into your hand. You gain life equal to its mana value";
    }

    private ForceMasteryEffect(final ForceMasteryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards(source, new CardsImpl(card), game);
                controller.moveCards(card, Zone.HAND, source, game);
                controller.gainLife(card.getManaValue(), game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public ForceMasteryEffect copy() {
        return new ForceMasteryEffect(this);
    }
}
