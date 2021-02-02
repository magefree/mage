package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ConfidenceFromStrength extends CardImpl {

    public ConfidenceFromStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Target creature gets +4/+4 until and gains trample until end of turn.
        Effect effect = new BoostTargetEffect(4, 4, Duration.EndOfTurn);
        effect.setText("Target creature gets +4/+4");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ConfidenceFromStrength(final ConfidenceFromStrength card) {
        super(card);
    }

    @Override
    public ConfidenceFromStrength copy() {
        return new ConfidenceFromStrength(this);
    }
}
