package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author muz
 */
public final class BlackPantherMostDangerous extends CardImpl {

    private static final FilterAnyTarget filter = new FilterAnyTarget("any other target");

    static {
        filter.getPermanentFilter().add(AnotherPredicate.instance);
    }

    public BlackPantherMostDangerous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Black Panther is dealt damage, he deals that much damage to any other target.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
            new DamageTargetEffect(SavedDamageValue.MUCH, "he")
                .setText("he deals that much damage to any other target"),
            false
        );
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);

        // Power-up -- {5}{W}{W}: Put two +1/+1 counters on Black Panther. Other creatures you control get +2/+2 until end of turn.
        ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new ManaCostsImpl<>("{5}{W}{W}")
        );
        ability.addEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn, true));
        this.addAbility(ability);
    }

    private BlackPantherMostDangerous(final BlackPantherMostDangerous card) {
        super(card);
    }

    @Override
    public BlackPantherMostDangerous copy() {
        return new BlackPantherMostDangerous(this);
    }
}
