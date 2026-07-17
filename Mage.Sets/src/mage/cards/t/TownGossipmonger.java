package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class TownGossipmonger extends TransformingDoubleFacedCard {

    public TownGossipmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "{W}",
                "Incited Rabble",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "R"
        );

        // Town Gossipmonger
        this.getLeftHalfCard().setPT(1, 1);

        // {T}, Tap an untapped creature you control: Transform Town Gossipmonger.
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE));
        this.getLeftHalfCard().addAbility(ability);

        // Incited Rabble
        this.getRightHalfCard().setPT(2, 3);

        // Incited Rabble attacks each combat if able.
        this.getRightHalfCard().addAbility(new AttacksEachCombatStaticAbility());

        // {2}: Incited Rabble gets +1/+0 until end of turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));
    }

    private TownGossipmonger(final TownGossipmonger card) {
        super(card);
    }

    @Override
    public TownGossipmonger copy() {
        return new TownGossipmonger(this);
    }
}
