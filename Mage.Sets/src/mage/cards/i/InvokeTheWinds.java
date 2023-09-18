package mage.cards.i;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvokeTheWinds extends CardImpl {

    public InvokeTheWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}{U}{U}");

        // Gain control of target artifact or creature. Untap it.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private InvokeTheWinds(final InvokeTheWinds card) {
        super(card);
    }

    @Override
    public InvokeTheWinds copy() {
        return new InvokeTheWinds(this);
    }
}
