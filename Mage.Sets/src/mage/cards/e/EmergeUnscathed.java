
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class EmergeUnscathed extends CardImpl {

    public EmergeUnscathed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Target creature you control gains protection from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        
        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private EmergeUnscathed(final EmergeUnscathed card) {
        super(card);
    }

    @Override
    public EmergeUnscathed copy() {
        return new EmergeUnscathed(this);
    }
}
