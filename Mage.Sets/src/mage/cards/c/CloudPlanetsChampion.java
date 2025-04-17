package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.cost.ReduceCostEquipTargetSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloudPlanetsChampion extends CardImpl {

    private static final Condition condition = new CompoundCondition(EquippedSourceCondition.instance, MyTurnCondition.instance);

    public CloudPlanetsChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // During your turn, as long as Cloud is equipped, it has double strike and indestructible.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance()), condition,
                "during your turn, as long as {this} is equipped, it has double strike"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance()),
                condition, "and indestructible"
        ));
        this.addAbility(ability);

        // Equip abilities you activate that target Cloud cost {2} less to activate.
        this.addAbility(new SimpleStaticAbility(new ReduceCostEquipTargetSourceEffect(2)));
    }

    private CloudPlanetsChampion(final CloudPlanetsChampion card) {
        super(card);
    }

    @Override
    public CloudPlanetsChampion copy() {
        return new CloudPlanetsChampion(this);
    }
}
