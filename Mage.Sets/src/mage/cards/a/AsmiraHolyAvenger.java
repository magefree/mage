
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 * @author Plopman
 */
public final class AsmiraHolyAvenger extends CardImpl {

    public AsmiraHolyAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of each end step, put a +1/+1 counter on Asmira, Holy Avenger for each creature put into your graveyard from the battlefield this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new AsmiraHolyAvengerDynamicValue(), true), TargetController.ANY, false), new AsmiraHolyAvengerWatcher());
    }

    private AsmiraHolyAvenger(final AsmiraHolyAvenger card) {
        super(card);
    }

    @Override
    public AsmiraHolyAvenger copy() {
        return new AsmiraHolyAvenger(this);
    }
}


class AsmiraHolyAvengerWatcher extends Watcher {

    private int creaturesCount = 0;

    public AsmiraHolyAvengerWatcher() {
        super(WatcherScope.PLAYER);
        condition = true;
    }

    public int getCreaturesCount() {
        return creaturesCount;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject card = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && ((Card) card).isOwnedBy(this.controllerId) && card.isCreature(game)) {
                creaturesCount++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creaturesCount = 0;
    }
}

class AsmiraHolyAvengerDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        AsmiraHolyAvengerWatcher watcher = game.getState().getWatcher(AsmiraHolyAvengerWatcher.class, sourceAbility.getControllerId());
        if (watcher != null) {
            return watcher.getCreaturesCount();
        }
        return 0;
    }

    @Override
    public AsmiraHolyAvengerDynamicValue copy() {
        return new AsmiraHolyAvengerDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "creature put into your graveyard from the battlefield this turn";
    }
}