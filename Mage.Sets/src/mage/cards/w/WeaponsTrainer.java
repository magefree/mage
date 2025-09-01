package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WeaponsTrainer extends CardImpl {

    private static final String rule = "Other creatures you control get +1/+0 as long as you control an Equipment.";

    public WeaponsTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Other creatures you control get +1/+0 as long as you control an Equipment.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, true),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT), rule);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private WeaponsTrainer(final WeaponsTrainer card) {
        super(card);
    }

    @Override
    public WeaponsTrainer copy() {
        return new WeaponsTrainer(this);
    }
}
