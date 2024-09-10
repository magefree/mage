
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class ClockworkBeetle extends CardImpl {

    public ClockworkBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Clockwork Beetle enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), "with two +1/+1 counters on it"));

        // Whenever Clockwork Beetle attacks or blocks, remove a +1/+1 counter from it at end of combat.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new ClockworkBeetleEffect(), false));
    }

    private ClockworkBeetle(final ClockworkBeetle card) {
        super(card);
    }

    @Override
    public ClockworkBeetle copy() {
        return new ClockworkBeetle(this);
    }
}

class ClockworkBeetleEffect extends OneShotEffect {

    ClockworkBeetleEffect() {
        super(Outcome.UnboostCreature);
        staticText = "remove a +1/+1 counter from {this} at end of combat";
    }

    private ClockworkBeetleEffect(final ClockworkBeetleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Effect effect = new RemoveCounterTargetEffect(CounterType.P1P1.createInstance());
            effect.setTargetPointer(new FixedTarget(source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
            game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(effect), source);
            return true;
        }
        return false;
    }

    @Override
    public ClockworkBeetleEffect copy() {
        return new ClockworkBeetleEffect(this);
    }

}
