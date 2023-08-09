package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WulfgarOfIcewindDale extends CardImpl {

    public WulfgarOfIcewindDale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Melee
        this.addAbility(new MeleeAbility());

        // If a creature you control attacking would cause a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new WulfgarOfIcewindDaleEffect()));
    }

    private WulfgarOfIcewindDale(final WulfgarOfIcewindDale card) {
        super(card);
    }

    @Override
    public WulfgarOfIcewindDale copy() {
        return new WulfgarOfIcewindDale(this);
    }
}

class WulfgarOfIcewindDaleEffect extends ReplacementEffectImpl {

    WulfgarOfIcewindDaleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a creature you control attacking would cause a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    WulfgarOfIcewindDaleEffect(final WulfgarOfIcewindDaleEffect effect) {
        super(effect);
    }

    @Override
    public WulfgarOfIcewindDaleEffect copy() {
        return new WulfgarOfIcewindDaleEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        Permanent sourcePermanent = game.getPermanent(numberOfTriggersEvent.getSourceId());
        if (sourcePermanent == null || !sourcePermanent.isControlledBy(source.getControllerId())) {
            return false;
        }
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null) {
            return false;
        }

        switch (sourceEvent.getType()) {
            case ATTACKER_DECLARED:
                return source.isControlledBy(sourceEvent.getPlayerId());
            case DECLARED_ATTACKERS:
                return game
                        .getCombat()
                        .getAttackers()
                        .stream()
                        .map(game::getControllerId)
                        .anyMatch(source::isControlledBy);
            case DEFENDER_ATTACKED:
                return ((DefenderAttackedEvent) sourceEvent)
                        .getAttackers(game)
                        .stream()
                        .map(Controllable::getControllerId)
                        .anyMatch(source::isControlledBy);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
