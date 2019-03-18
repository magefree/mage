
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 * If you would draw a card, instead you may put exactly X cards from the top of your library into your graveyard. If
 * you do, return this card from your graveyard to your hand. Otherwise, draw a card.
 *
 * @author North
 */
public class DredgeAbility extends SimpleStaticAbility {

    public DredgeAbility(int value) {
        super(Zone.GRAVEYARD, new DredgeEffect(value));
    }

    public DredgeAbility(final DredgeAbility ability) {
        super(ability);
    }

    @Override
    public DredgeAbility copy() {
        return new DredgeAbility(this);
    }
}

class DredgeEffect extends ReplacementEffectImpl {

    private final int amount;

    public DredgeEffect(int value) {
        super(Duration.WhileInGraveyard, Outcome.ReturnToHand);
        this.amount = value;
        this.staticText = new StringBuilder("Dredge ").append(Integer.toString(value)).append(" <i>(If you would draw a card, instead you may put exactly ").append(value).append(" card(s) from the top of your library into your graveyard. If you do, return this card from your graveyard to your hand. Otherwise, draw a card.)</i>").toString();
    }

    public DredgeEffect(final DredgeEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DredgeEffect copy() {
        return new DredgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() >= amount 
                && player.chooseUse(outcome, new StringBuilder("Dredge ").append(sourceCard.getLogName()).
                append("? (").append(amount).append(" cards go from top of library to graveyard)").toString(), source, game)) {
            if (!game.isSimulation()) {
                game.informPlayers(new StringBuilder(player.getLogName()).append(" dredges ").append(sourceCard.getLogName()).toString());
            }
            Cards cardsToGrave = new CardsImpl();
            cardsToGrave.addAll(player.getLibrary().getTopCards(game, amount));
            player.moveCards(cardsToGrave, Zone.GRAVEYARD, source, game);
            player.moveCards(sourceCard, Zone.HAND, source, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DRAW_CARD;
    }


    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            return controller != null && controller.getLibrary().size() >= amount;
        }
        return false;
    }
}
