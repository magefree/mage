
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RhonassLastStandToken;

/**
 *
 * @author spjspj
 */
public final class RhonassLastStand extends CardImpl {

    public RhonassLastStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // Create a 5/4 green Snake creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RhonassLastStandToken()));

        // Lands you control don't untap during your next untap step.
        this.getSpellAbility().addEffect(new DontUntapInControllersUntapStepAllEffect(
                Duration.UntilYourNextTurn, TargetController.YOU, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS));
    }

    private RhonassLastStand(final RhonassLastStand card) {
        super(card);
    }

    @Override
    public RhonassLastStand copy() {
        return new RhonassLastStand(this);
    }
}
