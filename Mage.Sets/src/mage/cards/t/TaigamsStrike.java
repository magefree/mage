
package mage.cards.t;

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
 * @author LevelX2
 */
public final class TaigamsStrike extends CardImpl {

    public TaigamsStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Target creature gets +2/+0 until end of turn and can't be blocked this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        Effect effect = new CantBeBlockedTargetEffect();
        effect.setText("and can't be blocked this turn");
        this.getSpellAbility().addEffect(effect);        
        
        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private TaigamsStrike(final TaigamsStrike card) {
        super(card);
    }

    @Override
    public TaigamsStrike copy() {
        return new TaigamsStrike(this);
    }
}
