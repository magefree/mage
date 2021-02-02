
package mage.cards.k;

import java.util.UUID;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class KefnetsLastWord extends CardImpl {

    public KefnetsLastWord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Gain control of target artifact, creature or enchantment. Lands you control don't untap during your next untap step.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom));
        this.getSpellAbility().addEffect(new DontUntapInControllersUntapStepAllEffect(
                Duration.UntilYourNextTurn, TargetController.YOU, new FilterControlledLandPermanent("Lands you control"))
                .setText("Lands you control don't untap during your next untap phase"));
    }

    private KefnetsLastWord(final KefnetsLastWord card) {
        super(card);
    }

    @Override
    public KefnetsLastWord copy() {
        return new KefnetsLastWord(this);
    }
}
