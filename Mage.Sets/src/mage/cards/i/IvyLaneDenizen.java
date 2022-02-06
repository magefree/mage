package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IvyLaneDenizen extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another green creature");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public IvyLaneDenizen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another green creature enters the battlefield under your control, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                filter, false, null, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private IvyLaneDenizen(final IvyLaneDenizen card) {
        super(card);
    }

    @Override
    public IvyLaneDenizen copy() {
        return new IvyLaneDenizen(this);
    }
}
