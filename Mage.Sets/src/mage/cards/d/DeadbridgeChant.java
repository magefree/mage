package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DeadbridgeChant extends CardImpl {

    public DeadbridgeChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{G}");

        // When Deadbridge Chant enters the battlefield, put the top ten cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(10)));

        // At the beginning of your upkeep, choose a card at random in your graveyard. If it's a creature card, put it onto the battlefield. Otherwise, put it into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DeadbridgeChantEffect(), TargetController.YOU, false));
    }

    private DeadbridgeChant(final DeadbridgeChant card) {
        super(card);
    }

    @Override
    public DeadbridgeChant copy() {
        return new DeadbridgeChant(this);
    }
}

class DeadbridgeChantEffect extends OneShotEffect {

    public DeadbridgeChantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "choose a card at random in your graveyard. If it's a creature card, put it onto the battlefield. Otherwise, put it into your hand";
    }

    private DeadbridgeChantEffect(final DeadbridgeChantEffect effect) {
        super(effect);
    }

    @Override
    public DeadbridgeChantEffect copy() {
        return new DeadbridgeChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && !controller.getGraveyard().isEmpty()) {
            Card card = controller.getGraveyard().getRandom(game);
            if (card != null) {
                Zone targetZone = Zone.HAND;
                String text = " put into hand of ";
                if (card.isCreature(game)) {
                    targetZone = Zone.BATTLEFIELD;
                    text = " put onto battlefield for ";
                }
                controller.moveCards(card, targetZone, source, game);
                game.informPlayers("Deadbridge Chant: " + card.getName() + text + controller.getLogName());
                return true;
            }
        }
        return false;
    }
}
