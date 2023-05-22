package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ChosenOfMarkov extends TransformingDoubleFacedCard {
    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.VAMPIRE, "untapped Vampire you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ChosenOfMarkov(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "{2}{B}",
                "Markov's Servant",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "B"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(4, 4);

        // {tap}, Tap an untapped Vampire you control: Transform Chosen of Markov.
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.getLeftHalfCard().addAbility(ability);
    }

    private ChosenOfMarkov(final ChosenOfMarkov card) {
        super(card);
    }

    @Override
    public ChosenOfMarkov copy() {
        return new ChosenOfMarkov(this);
    }
}
