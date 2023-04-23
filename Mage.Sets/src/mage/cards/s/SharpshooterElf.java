package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SharpshooterElf extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature with flying an opponent controls");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public SharpshooterElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Sharpshooter Elf's power is equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(CreaturesYouControlCount.instance)
        ));

        // When Sharpshooter Elf enters the battlefield, it deals damage equal to its power to target creature with flying an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(xValue, "it"), true
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SharpshooterElf(final SharpshooterElf card) {
        super(card);
    }

    @Override
    public SharpshooterElf copy() {
        return new SharpshooterElf(this);
    }
}
