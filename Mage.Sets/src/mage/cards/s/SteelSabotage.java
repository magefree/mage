package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class SteelSabotage extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact spell");
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public SteelSabotage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Choose one - Counter target artifact spell; or return target artifact to its owner's hand.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
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
