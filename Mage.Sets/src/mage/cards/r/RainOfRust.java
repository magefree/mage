
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public final class RainOfRust extends CardImpl {

    public RainOfRust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");

        // Choose one -
        //Destroy target artifact;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        //or destroy target land.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetLandPermanent());
        this.getSpellAbility().getModes().addMode(mode);
        // Entwine {3}{R}
        this.addAbility(new EntwineAbility("{3}{R}"));
    }

    private RainOfRust(final RainOfRust card) {
        super(card);
    }

    @Override
    public RainOfRust copy() {
        return new RainOfRust(this);
    }
}
