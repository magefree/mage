
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BattlewiseValor extends CardImpl {

    public BattlewiseValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Target creature gets +2/+2 until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2,2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ScryEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BattlewiseValor(final BattlewiseValor card) {
        super(card);
    }

    @Override
    public BattlewiseValor copy() {
        return new BattlewiseValor(this);
    }
}
