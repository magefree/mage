
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SurviveTheNight extends CardImpl {

    public SurviveTheNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Target creature gets +1/+0 and gains indestructible until end of turn.
        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains indestructible until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Investigate.
        this.getSpellAbility().addEffect(new InvestigateEffect().concatBy("<br>"));
    }

    private SurviveTheNight(final SurviveTheNight card) {
        super(card);
    }

    @Override
    public SurviveTheNight copy() {
        return new SurviveTheNight(this);
    }
}
