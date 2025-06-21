package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class TopanAscetic extends CardImpl {

    public TopanAscetic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap an untapped creature you control: Topan Ascetic gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)
        ));
    }

    private TopanAscetic(final TopanAscetic card) {
        super(card);
    }

    @Override
    public TopanAscetic copy() {
        return new TopanAscetic(this);
    }
}
