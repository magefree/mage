
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

/**
 *
 * @author maxlebedev
 */
public final class LeovoldEmissaryOfTrest extends CardImpl {

    public LeovoldEmissaryOfTrest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each opponent can't draw more than one card each turn.  (Based on SpiritOfTheLabyrinth)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeovoldEmissaryOfTrestEffect()), new CardsAmountDrawnThisTurnWatcher());

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new LeovoldEmissaryOfTrestTriggeredAbility());
    }

    private LeovoldEmissaryOfTrest(final LeovoldEmissaryOfTrest card) {
        super(card);
    }

    @Override
    public LeovoldEmissaryOfTrest copy() {
        return new LeovoldEmissaryOfTrest(this);
    }
}

class LeovoldEmissaryOfTrestEffect extends ContinuousRuleModifyingEffectImpl {

    public LeovoldEmissaryOfTrestEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Each opponent can't draw more than one card each turn";
    }

    public LeovoldEmissaryOfTrestEffect(final LeovoldEmissaryOfTrestEffect effect) {
        super(effect);
    }

    @Override
    public LeovoldEmissaryOfTrestEffect copy() {
        return new LeovoldEmissaryOfTrestEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        Player controller = game.getPlayer(source.getControllerId());
        return watcher != null && controller != null && watcher.getAmountCardsDrawn(event.getPlayerId()) >= 1
                && game.isOpponent(controller, event.getPlayerId());
    }

}

class LeovoldEmissaryOfTrestTriggeredAbility extends TriggeredAbilityImpl {

    LeovoldEmissaryOfTrestTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
        setTriggerPhrase("Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, ");
    }

    LeovoldEmissaryOfTrestTriggeredAbility(final LeovoldEmissaryOfTrestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LeovoldEmissaryOfTrestTriggeredAbility copy() {
        return new LeovoldEmissaryOfTrestTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        Player targetter = game.getPlayer(event.getPlayerId());
        if (controller != null && targetter != null
                && game.isOpponent(controller, targetter.getId())) {
            if (event.getTargetId().equals(controller.getId())) {
                return true; // Player was targeted
            }
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && this.isControlledBy(permanent.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}
