
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class OuterRimSlaver extends CardImpl {

    public OuterRimSlaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}");
        this.subtype.add(SubType.TRANDOSHAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Outer Rim Slaver enters the battlefield, you may put a bounty counter on target creature. If you do, another target creature fights that creature
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), true);
        ability.addEffect(new FightTargetsEffect("another target creature fights that creature"));
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setTargetTag(1);
        ability.addTarget(target);
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        ability.addTarget(target2);
        this.addAbility(ability);
    }

    public OuterRimSlaver(final OuterRimSlaver card) {
        super(card);
    }

    @Override
    public OuterRimSlaver copy() {
        return new OuterRimSlaver(this);
    }
}
