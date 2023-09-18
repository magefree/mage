package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UlvenwaldBehemoth extends CardImpl {

    public UlvenwaldBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.color.setGreen(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

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
        this.addAbility(ability);
    }

    private UlvenwaldBehemoth(final UlvenwaldBehemoth card) {
        super(card);
    }

    @Override
    public UlvenwaldBehemoth copy() {
        return new UlvenwaldBehemoth(this);
    }
}
