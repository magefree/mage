package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UlvenwaldOddity extends TransformingDoubleFacedCard {

    public UlvenwaldOddity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BEAST}, "{2}{G}{G}",
                "Ulvenwald Behemoth",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BEAST, SubType.HORROR}, "G"
        );

        // Ulvenwald Oddity
        this.getLeftHalfCard().setPT(4, 4);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // {5}{G}{G}: Transform Ulvenwald Oddity.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{5}{G}{G}")
        ));

        // Ulvenwald Behemoth
        this.getRightHalfCard().setPT(8, 8);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Other creatures you control get +1/+1 and have trample and haste.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).setText("and have trample"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).setText("and haste"));
        this.getRightHalfCard().addAbility(ability);
    }

    private UlvenwaldOddity(final UlvenwaldOddity card) {
        super(card);
    }

    @Override
    public UlvenwaldOddity copy() {
        return new UlvenwaldOddity(this);
    }
}
