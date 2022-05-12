package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ProvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class HunterSliver extends CardImpl {

    public HunterSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures have provoke.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new ProvokeAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ALL_SLIVERS
        ).setText("all Sliver creatures have provoke")));
    }

    private HunterSliver(final HunterSliver card) {
        super(card);
    }

    @Override
    public HunterSliver copy() {
        return new HunterSliver(this);
    }
}
