package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SibyllineSoothsayer extends CardImpl {

    public SibyllineSoothsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN, SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Temporal Foresight — When Sibylline Soothsayer enters the battlefield, reveal cards from the top of your library until you reveal a nonland card with mana value 3 or greater.
        // Exile that card with three time counters on it. If it doesn't have suspend, it gains suspend. Put the rest of the revealed cards on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SibyllineSoothsayerEffect()).withFlavorWord("Temporal Foresight"));
    }

    private SibyllineSoothsayer(final SibyllineSoothsayer card) {
        super(card);
    }

    @Override
    public SibyllineSoothsayer copy() {
        return new SibyllineSoothsayer(this);
    }
}

class SibyllineSoothsayerEffect extends OneShotEffect {

    SibyllineSoothsayerEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal a nonland card " +
                "with mana value 3 or greater. Exile that card with three time counters on it. " +
                "If it doesn't have suspend, it gains suspend. Put the rest of the revealed cards " +
                "on the bottom of your library in a random order.";
    }

    private SibyllineSoothsayerEffect(final SibyllineSoothsayerEffect effect) {
        super(effect);
    }

    @Override
    public SibyllineSoothsayerEffect copy() {
        return new SibyllineSoothsayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (!card.isLand(game) && card.getManaValue() >= 3) {
                player.revealCards(source, cards, game);
                player.moveCards(card, Zone.EXILED, source, game);
                SuspendAbility.addTimeCountersAndSuspend(card, 3, source, game);
                break;
            }
        }
        cards.retainZone(Zone.LIBRARY, game);
        if (!cards.isEmpty()) {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}
