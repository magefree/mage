
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ArtfulManeuver extends CardImpl {

    public ArtfulManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2,2,Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private ArtfulManeuver(final ArtfulManeuver card) {
        super(card);
    }

    @Override
    public ArtfulManeuver copy() {
        return new ArtfulManeuver(this);
    }
}
