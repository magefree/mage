package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakeshiftBattalion extends CardImpl {

    public MakeshiftBattalion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Makeshift Battalion and at least two other creatures attack, put a +1/+1 counter on Makeshift Battalion.
        this.addAbility(new MakeshiftBattalionTriggeredAbility());
    }

    private MakeshiftBattalion(final MakeshiftBattalion card) {
        super(card);
    }

    @Override
    public MakeshiftBattalion copy() {
        return new MakeshiftBattalion(this);
    }
}

class MakeshiftBattalionTriggeredAbility extends TriggeredAbilityImpl {

    MakeshiftBattalionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    private MakeshiftBattalionTriggeredAbility(final MakeshiftBattalionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MakeshiftBattalionTriggeredAbility copy() {
        return new MakeshiftBattalionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 3
                && game.getCombat().getAttackers().contains(this.sourceId);
    }

    @Override
    public String getRule() {
        return "Whenever {this} and at least two other creatures attack, "
                + "put a +1/+1 counter on {this}.";
    }
}
