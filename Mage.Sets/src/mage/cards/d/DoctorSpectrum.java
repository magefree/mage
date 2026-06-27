package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.WallColorlessToken;
import mage.target.common.TargetEnchantmentPermanent;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DoctorSpectrum extends CardImpl {

    private static final FilterControlledPermanent filter
        = new FilterControlledPermanent(SubType.HERO, "other Hero you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DoctorSpectrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Doctor Spectrum enters, choose one --
        // * Create a 0/4 colorless Wall creature token with defender.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new CreateTokenEffect(new WallColorlessToken())
        );

        // * Put a +1/+1 counter on each other Hero you control.
        ability.addMode(new Mode(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ));

        // * Destroy target enchantment.
        ability.addMode(new Mode(new DestroyTargetEffect())
            .addTarget(new TargetEnchantmentPermanent())
        );

        this.addAbility(ability);
    }

    private DoctorSpectrum(final DoctorSpectrum card) {
        super(card);
    }

    @Override
    public DoctorSpectrum copy() {
        return new DoctorSpectrum(this);
    }
}
