package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
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
public final class HollowheadSliver extends CardImpl {

    public HollowheadSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have "{T}, Discard a card: Draw a card."
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new TapSourceCost()
        );
        ability.addCost(new DiscardCardCost());
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private HollowheadSliver(final HollowheadSliver card) {
        super(card);
    }

    @Override
    public HollowheadSliver copy() {
        return new HollowheadSliver(this);
    }
}
