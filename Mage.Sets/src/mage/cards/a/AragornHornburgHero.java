package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.RenownedPredicate;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class AragornHornburgHero extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a renowned creature you control");

    static {
        filter.add(RenownedPredicate.instance);
    }
    public AragornHornburgHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Attacking creatures you control have first strike and renown 1.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new RenownAbility(1), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).setText("and renown 1"));
        this.addAbility(ability);
        // Whenever a renowned creature you control deals combat damage to a player, double the number of +1/+1 counters on it.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DoubleCountersTargetEffect(CounterType.P1P1), filter,
                false, SetTargetPointer.PERMANENT, true
        ));

    }

    private AragornHornburgHero(final AragornHornburgHero card) {
        super(card);
    }

    @Override
    public AragornHornburgHero copy() {
        return new AragornHornburgHero(this);
    }
}
