package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class Telethopter extends CardImpl {

    public Telethopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)
        ));
    }

    private Telethopter(final Telethopter card) {
        super(card);
    }

    @Override
    public Telethopter copy() {
        return new Telethopter(this);
    }
}
