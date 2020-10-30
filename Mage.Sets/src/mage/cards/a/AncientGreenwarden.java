package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientGreenwarden extends CardImpl {

    public AncientGreenwarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(new PlayLandsFromGraveyardEffect()));

        // If a land entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AncientGreenwardenEffect()));
    }

    private AncientGreenwarden(final AncientGreenwarden card) {
        super(card);
    }

    @Override
    public AncientGreenwarden copy() {
        return new AncientGreenwarden(this);
    }
}

class AncientGreenwardenEffect extends ReplacementEffectImpl {

    AncientGreenwardenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a land entering the battlefield causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private AncientGreenwardenEffect(final AncientGreenwardenEffect effect) {
        super(effect);
    }

    @Override
    public AncientGreenwardenEffect copy() {
        return new AncientGreenwardenEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof NumberOfTriggersEvent)) {
            return false;
        }
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null
                || sourceEvent.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || !(sourceEvent instanceof EntersTheBattlefieldEvent)
                || !((EntersTheBattlefieldEvent) sourceEvent).getTarget().isLand()) {
            return false;
        }
        return game.getPermanent(numberOfTriggersEvent.getSourceId()) != null;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
