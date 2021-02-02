

package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LokiX
 */
public final class MightOfOaks extends CardImpl {

    public MightOfOaks(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(7, 7, Duration.EndOfTurn));
    }

    private MightOfOaks(final MightOfOaks card) {
        super(card);
    }

    @Override
    public MightOfOaks copy() {
        return new MightOfOaks(this);
    }
}
