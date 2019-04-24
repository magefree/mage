package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JiangYangguWildcrafter extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("Each creature you control with a +1/+1 counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public JiangYangguWildcrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.YANGGU);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));

        // Each creature you control with a +1/+1 counter on it has "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield, filter
        )));

        // -1: Put a +1/+1 counter on target creature.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ), -1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private JiangYangguWildcrafter(final JiangYangguWildcrafter card) {
        super(card);
    }

    @Override
    public JiangYangguWildcrafter copy() {
        return new JiangYangguWildcrafter(this);
    }
}
