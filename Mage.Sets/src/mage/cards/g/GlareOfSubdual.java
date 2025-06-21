package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GlareOfSubdual extends CardImpl {

    public GlareOfSubdual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}");

        // Tap an untapped creature you control: Tap target artifact or creature.
        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect(),
                new TapTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE))
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private GlareOfSubdual(final GlareOfSubdual card) {
        super(card);
    }

    @Override
    public GlareOfSubdual copy() {
        return new GlareOfSubdual(this);
    }
}
