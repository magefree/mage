package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class BullRancor extends CardImpl {

    public BullRancor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}{G}{W}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {3}{R}{G}{G}{W}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{3}{R}{G}{G}{W}", 3));

        // As long as Bull Rancor is monstrous, creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(new MenaceAbility(false), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES),
                MonstrousCondition.instance,
                "As long as Bull Rancor is monstrous, creatures you control have menace")
        ));
    }

    private BullRancor(final BullRancor card) {
        super(card);
    }

    @Override
    public BullRancor copy() {
        return new BullRancor(this);
    }
}
