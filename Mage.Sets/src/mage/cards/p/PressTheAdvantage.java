
package mage.cards.p;

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
 * @author LevelX2
 */
public final class PressTheAdvantage extends CardImpl {

    public PressTheAdvantage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}{G}");

        // Up to two target creatures each get +2/+2 and gain trample until end of turn.
        Effect effect = new BoostTargetEffect(2,2, Duration.EndOfTurn);
        effect.setText("Up to two target creatures each get +2/+2");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, "and gain trample until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,2));
    }

    private PressTheAdvantage(final PressTheAdvantage card) {
        super(card);
    }

    @Override
    public PressTheAdvantage copy() {
        return new PressTheAdvantage(this);
    }
}
