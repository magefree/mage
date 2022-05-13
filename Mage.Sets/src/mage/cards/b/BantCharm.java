
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class BantCharm extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant spell");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public BantCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{W}{U}");


        // Choose one - Destroy target artifact;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        // or put target creature on the bottom of its owner's library;
        Mode mode = new Mode(new PutOnLibraryTargetEffect(false));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or counter target instant spell.
        mode = new Mode(new CounterTargetEffect());
        mode.addTarget(new TargetSpell(filter));
        this.getSpellAbility().addMode(mode);
    }

    private BantCharm(final BantCharm card) {
        super(card);
    }

    @Override
    public BantCharm copy() {
        return new BantCharm(this);
    }
}
