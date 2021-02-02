package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class DistortionStrike extends CardImpl {

    public DistortionStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Target creature gets +1/+0 until end of turn and can't be blocked this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        Effect effect = new CantBeBlockedTargetEffect();
        effect.setText("and can't be blocked this turn");
        this.getSpellAbility().addEffect(effect);

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private DistortionStrike(final DistortionStrike card) {
        super(card);
    }

    @Override
    public DistortionStrike copy() {
        return new DistortionStrike(this);
    }
}
