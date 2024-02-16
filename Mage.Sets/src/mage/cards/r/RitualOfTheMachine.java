
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class RitualOfTheMachine extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact, nonblack creature");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public RitualOfTheMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // As an additional cost to cast Ritual of the Machine, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        // Gain control of target nonartifact, nonblack creature.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfGame));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private RitualOfTheMachine(final RitualOfTheMachine card) {
        super(card);
    }

    @Override
    public RitualOfTheMachine copy() {
        return new RitualOfTheMachine(this);
    }
}
