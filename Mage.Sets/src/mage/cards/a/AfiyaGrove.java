package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCountersFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class AfiyaGrove extends CardImpl {

    public AfiyaGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Afiya Grove enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));

        // At the beginning of your upkeep, move a +1/+1 counter from Afiya Grove onto target creature.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new MoveCountersFromSourceToTargetEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // When Afiya Grove has no +1/+1 counters on it, sacrifice it.
        this.addAbility(new AfiyaGroveNoCountersAbility());
    }

    private AfiyaGrove(final AfiyaGrove card) {
        super(card);
    }

    @Override
    public AfiyaGrove copy() {
        return new AfiyaGrove(this);
    }
}

class AfiyaGroveNoCountersAbility extends StateTriggeredAbility {

    public AfiyaGroveNoCountersAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    private AfiyaGroveNoCountersAbility(final AfiyaGroveNoCountersAbility ability) {
        super(ability);
    }

    @Override
    public AfiyaGroveNoCountersAbility copy() {
        return new AfiyaGroveNoCountersAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) == 0;
    }

    @Override
    public String getRule() {
        return "When {this} has no +1/+1 counters on it, sacrifice it.";
    }

}
