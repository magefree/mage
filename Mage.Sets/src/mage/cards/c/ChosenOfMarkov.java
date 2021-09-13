
package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class ChosenOfMarkov extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Vampire you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public ChosenOfMarkov(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.m.MarkovsServant.class;

        // {tap}, Tap an untapped Vampire you control: Transform Chosen of Markov.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private ChosenOfMarkov(final ChosenOfMarkov card) {
        super(card);
    }

    @Override
    public ChosenOfMarkov copy() {
        return new ChosenOfMarkov(this);
    }
}
