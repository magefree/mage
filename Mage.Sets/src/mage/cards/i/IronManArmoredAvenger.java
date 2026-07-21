package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class IronManArmoredAvenger extends CardImpl {

    private static final FilterAttackingCreature filter =
            new FilterAttackingCreature("attacking modified creatures");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public IronManArmoredAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, put a +1/+1 counter on target creature.
        Ability ability = new DrawCardControllerTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever Iron Man attacks, other attacking modified creatures gain flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filter, true)
        ));
    }

    private IronManArmoredAvenger(final IronManArmoredAvenger card) {
        super(card);
    }

    @Override
    public IronManArmoredAvenger copy() {
        return new IronManArmoredAvenger(this);
    }
}
