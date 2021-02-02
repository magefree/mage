

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ScuteMob extends CardImpl {

    public ScuteMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new ScuteMobAbility());
    }

    private ScuteMob(final ScuteMob card) {
        super(card);
    }

    @Override
    public ScuteMob copy() {
        return new ScuteMob(this);
    }

}

class ScuteMobAbility extends TriggeredAbilityImpl {

    private FilterLandPermanent filter = new FilterLandPermanent();

    public ScuteMobAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)));
    }

    public ScuteMobAbility(final ScuteMobAbility ability) {
        super(ability);
    }

    @Override
    public ScuteMobAbility copy() {
        return new ScuteMobAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) >= 5;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control five or more lands, put four +1/+1 counters on {this}.";
    }
}