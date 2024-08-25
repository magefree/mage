
package mage.cards.g;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.AssistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.PowerTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GangUp extends CardImpl {

    public GangUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Assist
        this.addAbility(new AssistAbility());

        // Destroy target creature with power X or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("destroy target creature with power X or less"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().setTargetAdjuster(new PowerTargetAdjuster(ComparisonType.OR_LESS));
    }

    private GangUp(final GangUp card) {
        super(card);
    }

    @Override
    public GangUp copy() {
        return new GangUp(this);
    }
}
