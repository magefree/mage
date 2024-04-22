package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HancockGhoulishMayor extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(null);
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.or(
                SubType.ZOMBIE.getPredicate(),
                SubType.MUTANT.getPredicate()
        ));
    }

    public HancockGhoulishMayor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Each other creature you control that's a Zombie or Mutant gets +X/+X, where X is the number of counters on Hancock, Ghoulish Mayor.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                xValue, xValue, Duration.WhileOnBattlefield, filter, true
        ).setText("each other creature you control that's a Zombie or Mutant " +
                "gets +X/+X, where X is the number of counters on {this}")));

        // Undying
        this.addAbility(new UndyingAbility());
    }

    private HancockGhoulishMayor(final HancockGhoulishMayor card) {
        super(card);
    }

    @Override
    public HancockGhoulishMayor copy() {
        return new HancockGhoulishMayor(this);
    }
}
