package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HeartSliver extends CardImpl {

    public HeartSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ALL_SLIVERS, false
        )));
    }

    private HeartSliver(final HeartSliver card) {
        super(card);
    }

    @Override
    public HeartSliver copy() {
        return new HeartSliver(this);
    }
}
