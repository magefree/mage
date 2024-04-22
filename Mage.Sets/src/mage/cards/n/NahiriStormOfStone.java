package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NahiriStormOfStone extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public NahiriStormOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R/W}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAHIRI);
        this.setStartingLoyalty(6);

        // As long as it's your turn, creatures you control have first strike and equip abilities you activate cost {1} less to activate.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                ), MyTurnCondition.instance, "As long as it's your turn, " +
                "creatures you control have first strike"
        ));
        ability.addEffect(new ConditionalCostModificationEffect(
                new AbilitiesCostReductionControllerEffect(
                        EquipAbility.class, "Equip"
                ), MyTurnCondition.instance, "and equip abilities you activate cost {1} less to activate"
        ));
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);

        // -X: Nahiri, Storm of Stone deals X damage to target tapped creature.
        ability = new LoyaltyAbility(new DamageTargetEffect(GetXLoyaltyValue.instance));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private NahiriStormOfStone(final NahiriStormOfStone card) {
        super(card);
    }

    @Override
    public NahiriStormOfStone copy() {
        return new NahiriStormOfStone(this);
    }
}
