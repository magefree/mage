
package mage.abilities.effects.common;

import java.util.Locale;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class EnterBattlefieldPayCostOrPutGraveyardEffect extends ReplacementEffectImpl {

    private final Cost cost;

    public EnterBattlefieldPayCostOrPutGraveyardEffect(Cost cost) {
        super(Duration.EndOfGame, Outcome.PutCardInPlay);
        this.cost = cost;
        staticText = "If {this} would enter the battlefield, " + cost.getText() + " instead. If you do, put {this} onto the battlefield. If you don't, put it into its owner's graveyard";
    }

    public EnterBattlefieldPayCostOrPutGraveyardEffect(final EnterBattlefieldPayCostOrPutGraveyardEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public EnterBattlefieldPayCostOrPutGraveyardEffect copy() {
        return new EnterBattlefieldPayCostOrPutGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && cost != null && sourceObject != null) {
            boolean replace = true;
            if (cost.canPay(source, source, player.getId(), game)) {
                if (player.chooseUse(outcome,
                        cost.getText().substring(0, 1).toUpperCase(Locale.ENGLISH) + cost.getText().substring(1)
                        + "? (otherwise " + sourceObject.getLogName() + " is put into graveyard)", source, game)) {
                    cost.clearPaid();
                    replace = !cost.pay(source, game, source, source.getControllerId(), false, null);
                }
            }
            if (replace) {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    player.moveCards(card, Zone.GRAVEYARD, source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.getSourceId().equals(event.getTargetId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }

}
