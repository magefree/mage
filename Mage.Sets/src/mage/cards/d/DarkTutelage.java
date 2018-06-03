
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DarkTutelage extends CardImpl {

    public DarkTutelage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new DarkTutelageEffect(), false));
    }

    public DarkTutelage(final DarkTutelage card) {
        super(card);
    }

    @Override
    public DarkTutelage copy() {
        return new DarkTutelage(this);
    }

}

class DarkTutelageEffect extends OneShotEffect {

    public DarkTutelageEffect() {
        super(Outcome.DrawCard);
        staticText = "reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost";
    }

    public DarkTutelageEffect(final DarkTutelageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards(source, new CardsImpl(card), game);
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                controller.loseLife(card.getConvertedManaCost(), game, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public DarkTutelageEffect copy() {
        return new DarkTutelageEffect(this);
    }

}
