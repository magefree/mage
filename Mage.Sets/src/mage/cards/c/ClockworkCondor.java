
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class ClockworkCondor extends CardImpl {

    public ClockworkCondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new ClockworkCondorEffect(), false));
    }

    private ClockworkCondor(final ClockworkCondor card) {
        super(card);
    }

    @Override
    public ClockworkCondor copy() {
        return new ClockworkCondor(this);
    }
}

class ClockworkCondorEffect extends OneShotEffect {

    ClockworkCondorEffect() {
        super(Outcome.UnboostCreature);
        staticText = "remove a +1/+1 counter from {this} at end of combat";
    }

    private ClockworkCondorEffect(final ClockworkCondorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            AtTheEndOfCombatDelayedTriggeredAbility ability = new AtTheEndOfCombatDelayedTriggeredAbility(new RemoveCounterSourceEffect(CounterType.P1P1.createInstance()));
            game.addDelayedTriggeredAbility(ability, source);
        }
        return false;
    }

    @Override
    public ClockworkCondorEffect copy() {
        return new ClockworkCondorEffect(this);
    }

}
