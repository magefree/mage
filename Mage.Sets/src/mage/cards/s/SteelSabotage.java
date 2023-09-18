
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactSpell;
import mage.target.TargetSpell;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author North
 */
public final class SteelSabotage extends CardImpl {
    public SteelSabotage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Choose one - Counter target artifact spell; or return target artifact to its owner's hand.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(new FilterArtifactSpell()));
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private SteelSabotage(final SteelSabotage card) {
        super(card);
    }

    @Override
    public SteelSabotage copy() {
        return new SteelSabotage(this);
    }
}
