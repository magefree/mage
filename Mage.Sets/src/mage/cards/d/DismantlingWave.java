package mage.cards.d;

import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachOpponentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DismantlingWave extends CardImpl {

    public DismantlingWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // For each opponent, destroy up to one target artifact or enchantment that player controls.
        this.getSpellAbility().addEffect(new DestroyTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("For each opponent, destroy up to one target artifact or enchantment that player controls."));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().setTargetAdjuster(new ForEachOpponentTargetsAdjuster());

        // Cycling {6}{W}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{6}{W}{W}")));

        // When you cycle Dismantling Wave, destroy all artifacts and enchantments.
        this.addAbility(new CycleTriggeredAbility(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS_AND_ENCHANTMENTS)));
    }

    private DismantlingWave(final DismantlingWave card) {
        super(card);
    }

    @Override
    public DismantlingWave copy() {
        return new DismantlingWave(this);
    }
}
