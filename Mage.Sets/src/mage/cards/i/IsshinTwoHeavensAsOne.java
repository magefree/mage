package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IsshinTwoHeavensAsOne extends CardImpl {

    public IsshinTwoHeavensAsOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a creature attacking causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new IsshinTwoHeavensAsOneEffect()));
    }

    private IsshinTwoHeavensAsOne(final IsshinTwoHeavensAsOne card) {
        super(card);
    }

    @Override
    public IsshinTwoHeavensAsOne copy() {
        return new IsshinTwoHeavensAsOne(this);
    }
}

class IsshinTwoHeavensAsOneEffect extends ReplacementEffectImpl {

    IsshinTwoHeavensAsOneEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a creature attacking causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    IsshinTwoHeavensAsOneEffect(final IsshinTwoHeavensAsOneEffect effect) {
        super(effect);
    }

    @Override
    public IsshinTwoHeavensAsOneEffect copy() {
        return new IsshinTwoHeavensAsOneEffect(this);
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
        switch (sourceEvent.getType()) {
            case ATTACKER_DECLARED:
            case DECLARED_ATTACKERS:
            case DEFENDER_ATTACKED:
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
