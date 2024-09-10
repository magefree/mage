package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class CathedralAcolyte extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature that entered the battlefield this turn");

    static {
        filter.add(CounterAnyPredicate.instance);
        filter2.add(EnteredThisTurnPredicate.instance);
    }

    public CathedralAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Each creature you control with a counter on it has ward {1}.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield, filter
                ).setText("Each creature you control with a counter on it has ward {1}")
        ));

        // {T}: Put a +1/+1 counter on target creature that entered the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)), new TapSourceCost()
        );
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private CathedralAcolyte(final CathedralAcolyte card) {
        super(card);
    }

    @Override
    public CathedralAcolyte copy() {
        return new CathedralAcolyte(this);
    }
}
