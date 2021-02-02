
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class RunAground extends CardImpl {

    public RunAground(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Put target artifact or creature on top of it's owner's library.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
    }

    private RunAground(final RunAground card) {
        super(card);
    }

    @Override
    public RunAground copy() {
        return new RunAground(this);
    }
}
