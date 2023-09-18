package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SiegeZombie extends CardImpl {

    public SiegeZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap three untapped creatures you control: Each opponent loses 1 life.
        this.addAbility(new SimpleActivatedAbility(
                new LoseLifeOpponentsEffect(1),
                new TapTargetCost(new TargetControlledPermanent(
                        3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                ))
        ));
    }

    private SiegeZombie(final SiegeZombie card) {
        super(card);
    }

    @Override
    public SiegeZombie copy() {
        return new SiegeZombie(this);
    }
}
