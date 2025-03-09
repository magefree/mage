package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.MageObject;
import mage.util.CardUtil;

/**
 *
 * @author padfoot
 */
public final class EnsnaredByTheMara extends CardImpl {

    public EnsnaredByTheMara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");
        

        // Each opponent faces a villainous choice -- They exile cards from the top of their library until they exile a nonland card, then you may cast that card without paying its mana cost, or that player exiles the top four cards of their library and Ensnared by the Mara deals damage equal to the total mana value of those exiled cards to that player.
        this.getSpellAbility().addEffect(new EnsnaredByTheMaraEffect());
    }

    private EnsnaredByTheMara(final EnsnaredByTheMara card) {
        super(card);
    }

    @Override
    public EnsnaredByTheMara copy() {
        return new EnsnaredByTheMara(this);
    }
}

class EnsnaredByTheMaraEffect extends OneShotEffect {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
	    Outcome.PlayForFree, new EnsnaredByTheMaraFirstChoice(), new EnsnaredByTheMaraSecondChoice()
	);

    EnsnaredByTheMaraEffect() {
	super(Outcome.Benefit);
	staticText = "each opponent " + choice.generateRule();
    }

    private EnsnaredByTheMaraEffect(final EnsnaredByTheMaraEffect effect) {
	super(effect);
    }

    @Override
    public EnsnaredByTheMaraEffect copy() {
	return new EnsnaredByTheMaraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            choice.faceChoice(player, game, source);
        }
        return true;
    }
}

class EnsnaredByTheMaraFirstChoice extends VillainousChoice {
    EnsnaredByTheMaraFirstChoice() {
        super("They exile cards from the top of their library until they exile a nonland card, then you may cast that card without paying its mana cost","Exile a card for {controller} to cast for free");
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
        super("that player exiles the top four cards of their library and Ensnared by the Mara deals damage equal to the total mana value of those exiled cards to that player","exile four cards and take damage");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        int totalManaValue = 0; 
	Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
	player.moveCards(cards, Zone.EXILED, source, game);
	totalManaValue = cards
		.getCards(game)
		.stream()
		.mapToInt(MageObject::getManaValue)
		.sum();
	player.damage(totalManaValue, source, game);
        return true;	
    }
}
