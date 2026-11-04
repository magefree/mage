package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class DriftgloomCoyote extends CardImpl {
    public DriftgloomCoyote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.COYOTE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Driftgloom Coyote enters, exile target creature an opponent controls until Driftgloom Coyote leaves the battlefield. If that creature had power 2 or less, put a +1/+1 counter on Driftgloom Coyote.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                DriftgloomCoyoteCondition.instance));
        this.addAbility(ability);
    }

    private DriftgloomCoyote(final DriftgloomCoyote card) {
        super(card);
    }

    @Override
    public DriftgloomCoyote copy() {
        return new DriftgloomCoyote(this);
    }
}

enum DriftgloomCoyoteCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        // the creature is already exiled, so it needs last known information
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        return permanent != null && permanent.getPower().getValue() <= 2;
    }

    @Override
    public String toString() {
        return "that creature had power 2 or less";
    }
}
