
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class ForceReflex extends CardImpl {

    public ForceReflex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Untap target creature. It gets +1/+0 and gains first strike until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());

        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        effect.setText("It gets +1/+0");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Scry 1
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private ForceReflex(final ForceReflex card) {
        super(card);
    }

    @Override
    public ForceReflex copy() {
        return new ForceReflex(this);
    }
}
