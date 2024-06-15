package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TerritoryCuller extends CardImpl {

    public TerritoryCuller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Landfall -- Whenever a land enters the battlefield under your control, look at the top card of your library. If it's a creature card, you may reveal it and put it into your hand. If you don't put the card into your hand, you may put it into your graveyard.
        this.addAbility(new LandfallAbility(new TerritoryCullerEffect()));
    }

    private TerritoryCuller(final TerritoryCuller card) {
        super(card);
    }

    @Override
    public TerritoryCuller copy() {
        return new TerritoryCuller(this);
    }
}

class TerritoryCullerEffect extends OneShotEffect {

    TerritoryCullerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. If it's a creature card, you may reveal it and put it into your hand. "
                + "If you don't put the card into your hand, you may put it into your graveyard";
    }

    private TerritoryCullerEffect(final TerritoryCullerEffect effect) {
        super(effect);
    }

    @Override
    public TerritoryCullerEffect copy() {
        return new TerritoryCullerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.lookAtCards(source, "", new CardsImpl(card), game);
        if (card.isCreature(game)) {
            String message = "Put " + card.getName() + " " + PutCards.HAND.getMessage(false, false);
            if (controller.chooseUse(outcome, message, source, game)) {
                controller.revealCards(source, new CardsImpl(card), game);
                if (PutCards.HAND.moveCard(controller, card, source, game, "")) {
                    return true;
                }
            }
        }
        String message = "Put " + card.getName() + " " + PutCards.GRAVEYARD.getMessage(false, false);
        if (controller.chooseUse(Outcome.Discard, message, source, game)) {
            PutCards.GRAVEYARD.moveCard(controller, card, source, game, "");
        }
        return true;
    }
}
