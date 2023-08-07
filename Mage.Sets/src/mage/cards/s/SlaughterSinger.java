package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 * @author TheElk801
 */
public final class SlaughterSinger extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("another creature you control with toxic");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new AbilityPredicate(ToxicAbility.class));
    }

    public SlaughterSinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Toxic 2
        this.addAbility(new ToxicAbility(2));

        // Whenever another creature you control with toxic attacks, it gets +1/+1 until end of turn.
        this.addAbility(new AttacksAllTriggeredAbility(
                new BoostTargetEffect(1, 1).setText("it gets +1/+1 until end of turn"),
                false, filter, SetTargetPointer.PERMANENT, false
        ));
    }

    private SlaughterSinger(final SlaughterSinger card) {
        super(card);
    }

    @Override
    public SlaughterSinger copy() {
        return new SlaughterSinger(this);
    }
}
