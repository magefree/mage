package mage.cards.l;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class LongLostLances extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creatures you control that are equipped");

    static {
        filter.add(EquippedPredicate.instance);
    }

    public LongLostLances(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // During your turn, creatures you control that are equipped have first strike and vigilance.
        Ability ability = new SimpleStaticAbility(
            new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                    FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
                ), MyTurnCondition.instance,
                "During your turn, creatures you control that are equipped have first strike"
            )
        );
        ability.addEffect(
            new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                    VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter
                ), MyTurnCondition.instance,
                "and vigilance"
            )
        );
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));

    }

    private LongLostLances(final LongLostLances card) {
        super(card);
    }

    @Override
    public LongLostLances copy() {
        return new LongLostLances(this);
    }
}
