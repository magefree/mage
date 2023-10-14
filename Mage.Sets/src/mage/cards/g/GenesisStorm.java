package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CommanderStormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class GenesisStorm extends CardImpl {

    public GenesisStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // When you cast this spell, copy it for each time you've cast your commander from the command zone this game.
        this.addAbility(new CommanderStormAbility());

        // Reveal cards from the top of your library until you reveal a nonland permanent card. You may put that card onto the battlefield. Then put all cards revealed this way that weren't put onto the battlefield on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new GenesisStormEffect());
    }

    private GenesisStorm(final GenesisStorm card) {
        super(card);
    }

    @Override
    public GenesisStorm copy() {
        return new GenesisStorm(this);
    }
}

class GenesisStormEffect extends OneShotEffect {

    public GenesisStormEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "reveal cards from the top of your library "
                + "until you reveal a nonland permanent card. "
                + "You may put that card onto the battlefield. "
                + "Then put all cards revealed this way "
                + "that weren't put onto the battlefield "
                + "on the bottom of your library in a random order";
    }

    private GenesisStormEffect(final GenesisStormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards toReveal = new CardsImpl();
        Card nonLandCard = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.isPermanent(game) && !card.isLand(game)) {
                nonLandCard = card;
                break;
            }
        }
        controller.revealCards(source, toReveal, game);
        if (nonLandCard != null && controller.chooseUse(
                outcome, "Put " + nonLandCard.getLogName()
                + " onto the battlefield?", source, game
        )) {
            controller.moveCards(nonLandCard, Zone.BATTLEFIELD, source, game);
            toReveal.remove(nonLandCard);
        }
        controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }

    @Override
    public GenesisStormEffect copy() {
        return new GenesisStormEffect(this);
    }
}
