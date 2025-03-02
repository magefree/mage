package mage.cards.h;

import java.util.UUID;

import mage.abilities.condition.common.SacrificedPermanentCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author Jmlundeen
 */
public final class HellishSideswipe extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public HellishSideswipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");
        

        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
        // Destroy target creature or Vehicle. If the sacrificed permanent was a Vehicle, draw a card.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy target creature or Vehicle"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new SacrificedPermanentCondition(new FilterPermanent(SubType.VEHICLE, "permanent was a Vehicle"))
        ));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private HellishSideswipe(final HellishSideswipe card) {
        super(card);
    }

    @Override
    public HellishSideswipe copy() {
        return new HellishSideswipe(this);
    }
}
