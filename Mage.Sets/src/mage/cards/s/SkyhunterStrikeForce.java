package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyhunterStrikeForce extends CardImpl {

    public SkyhunterStrikeForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Melee
        this.addAbility(new MeleeAbility());

        // Lieutenant -- As long as you control your commander, other creatures you control have melee.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        new MeleeAbility(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CONTROLLED_CREATURES, true
                ), CommanderInPlayCondition.instance, "as long as you control your commander, " +
                "other creatures you control have melee"
        )).setAbilityWord(AbilityWord.LIEUTENANT));
    }

    private SkyhunterStrikeForce(final SkyhunterStrikeForce card) {
        super(card);
    }

    @Override
    public SkyhunterStrikeForce copy() {
        return new SkyhunterStrikeForce(this);
    }
}
