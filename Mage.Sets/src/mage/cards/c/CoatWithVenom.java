
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CoatWithVenom extends CardImpl {

    public CoatWithVenom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Target creature gets +1/+2 and gains deathtouch until end of turn.
        Effect effect = new BoostTargetEffect(1, 2, Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+2");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains deathtouch until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CoatWithVenom(final CoatWithVenom card) {
        super(card);
    }

    @Override
    public CoatWithVenom copy() {
        return new CoatWithVenom(this);
    }
}
