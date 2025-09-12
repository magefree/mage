package mage.cards.o;

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
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
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
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance())
                .setText("you may put a bounty counter on target creature"), true);
        ability.addEffect(new FightTargetsEffect().setText("If you do, another target creature fights that creature"));
        ability.addTarget(new TargetPermanent(new FilterCreaturePermanent("creature to put a bounty counter on it")).setTargetTag(1));
        FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature to fight that creature that gets the bounty counter");
        filter.add(new AnotherTargetPredicate(2));
        ability.addTarget(new TargetPermanent(filter).setTargetTag(2));
        this.addAbility(ability);
    }

    private OuterRimSlaver(final OuterRimSlaver card) {
        super(card);
    }

    @Override
    public OuterRimSlaver copy() {
        return new OuterRimSlaver(this);
    }
}
