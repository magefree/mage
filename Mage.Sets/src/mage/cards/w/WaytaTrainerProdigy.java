package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jimga150
 */
public final class WaytaTrainerProdigy extends CardImpl {

    public WaytaTrainerProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {2}{G}, {T}: Target creature you control fights another target creature. This ability costs {2} less to activate if it targets two creatures you control.
        
        //  If a creature you control being dealt damage causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        // Based on Isshin, Two Heavens as One
        this.addAbility(new SimpleStaticAbility(new WaytaTrainerProdigyEffect()));
    }

    private WaytaTrainerProdigy(final WaytaTrainerProdigy card) {
        super(card);
    }

    @Override
    public WaytaTrainerProdigy copy() {
        return new WaytaTrainerProdigy(this);
    }
}

class WaytaTrainerProdigyEffect extends ReplacementEffectImpl {

    WaytaTrainerProdigyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature you control being dealt damage causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private WaytaTrainerProdigyEffect(final WaytaTrainerProdigyEffect effect) {
        super(effect);
    }

    @Override
    public WaytaTrainerProdigyEffect copy() {
        return new WaytaTrainerProdigyEffect(this);
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
            case DAMAGED_BATCH_FOR_PERMANENTS:
            case DAMAGE_PERMANENT:
            case DAMAGED_PERMANENT:
            case COMBAT_DAMAGE_STEP:
            case COMBAT_DAMAGE_STEP_PRE:
            case COMBAT_DAMAGE_STEP_PRIORITY:
            case COMBAT_DAMAGE_STEP_POST:
                return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
