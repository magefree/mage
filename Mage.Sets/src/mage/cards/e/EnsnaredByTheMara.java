package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.FaceVillainousChoiceOpponentsEffect;
import mage.cards.*;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author padfoot
 */
public final class EnsnaredByTheMara extends CardImpl {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.PlayForFree, new EnsnaredByTheMaraFirstChoice(), new EnsnaredByTheMaraSecondChoice()
    );

    public EnsnaredByTheMara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Each opponent faces a villainous choice -- They exile cards from the top of their library until they exile a nonland card, then you may cast that card without paying its mana cost, or that player exiles the top four cards of their library and Ensnared by the Mara deals damage equal to the total mana value of those exiled cards to that player.
        this.getSpellAbility().addEffect(new FaceVillainousChoiceOpponentsEffect(choice));
    }

    private EnsnaredByTheMara(final EnsnaredByTheMara card) {
        super(card);
    }

    @Override
    public EnsnaredByTheMara copy() {
        return new EnsnaredByTheMara(this);
    }
}

class EnsnaredByTheMaraFirstChoice extends VillainousChoice {
    EnsnaredByTheMaraFirstChoice() {
        super("They exile cards from the top of their library until they exile a nonland card, then you may cast that card without paying its mana cost", "Exile a card for {controller} to cast for free");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            if (!card.isLand(game)) {
                CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
                break;
            }
        }
        return true;
    }
}

class EnsnaredByTheMaraSecondChoice extends VillainousChoice {
    EnsnaredByTheMaraSecondChoice() {
        super("that player exiles the top four cards of their library and {this} deals damage equal to the total mana value of those exiled cards to that player", "exile four cards and take damage");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        player.moveCards(cards, Zone.EXILED, source, game);
        int totalManaValue = cards
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .sum();
        player.damage(totalManaValue, source, game);
        return true;
    }
}
