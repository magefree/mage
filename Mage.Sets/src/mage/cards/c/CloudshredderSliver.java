package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
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
public final class CloudshredderSliver extends CardImpl {

    public CloudshredderSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sliver creatures you control have flying and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ALL_SLIVERS
        ).setText("Sliver creatures you control have flying"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ALL_SLIVERS
        ).setText("and haste"));
        this.addAbility(ability);
    }

    private CloudshredderSliver(final CloudshredderSliver card) {
        super(card);
    }

    @Override
    public CloudshredderSliver copy() {
        return new CloudshredderSliver(this);
    }
}
