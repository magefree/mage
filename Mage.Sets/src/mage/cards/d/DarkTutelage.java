package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class DarkTutelage extends CardImpl {

    public DarkTutelage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DarkTutelageEffect(), TargetController.YOU, false
        ));
    }

    private DarkTutelage(final DarkTutelage card) {
        super(card);
    }

    @Override
    public DarkTutelage copy() {
        return new DarkTutelage(this);
    }
}

class DarkTutelageEffect extends OneShotEffect {

    DarkTutelageEffect() {
        super(Outcome.DrawCard);
        staticText = "reveal the top card of your library and put that card into your hand. You lose life equal to its mana value";
    }

    private DarkTutelageEffect(final DarkTutelageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        player.moveCards(card, Zone.HAND, source, game);
        if (card.getManaValue() > 0) {
            player.loseLife(card.getManaValue(), game, source, false);
        }
        return true;
    }

    @Override
    public DarkTutelageEffect copy() {
        return new DarkTutelageEffect(this);
    }
}
