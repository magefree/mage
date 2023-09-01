package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class TownGossipmonger extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public TownGossipmonger(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "{W}",
                "Incited Rabble",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "R"
        );
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(2, 3);

        // {T}, Tap an untapped creature you control: Transform Town Gossipmonger.
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.getLeftHalfCard().addAbility(ability);

        // Incited Rabble
        // Incited Rabble attacks each combat if able.
        this.getRightHalfCard().addAbility(new AttacksEachCombatStaticAbility());

        // {2}: Incited Rabble gets +1/+0 until end of turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new GenericManaCost(2)
        ));
    }

    private TownGossipmonger(final TownGossipmonger card) {
        super(card);
    }

    @Override
    public TownGossipmonger copy() {
        return new TownGossipmonger(this);
    }
}