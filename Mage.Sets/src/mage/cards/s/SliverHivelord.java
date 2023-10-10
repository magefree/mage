package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SliverHivelord extends CardImpl {

    public SliverHivelord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sliver creatures you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private SliverHivelord(final SliverHivelord card) {
        super(card);
    }

    @Override
    public SliverHivelord copy() {
        return new SliverHivelord(this);
    }
}
