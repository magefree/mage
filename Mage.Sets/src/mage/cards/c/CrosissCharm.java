
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CrosissCharm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public CrosissCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{B}{R}");


        // Choose one - Return target permanent to its owner's hand;
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        // or destroy target nonblack creature, and it can't be regenerated;
        Mode mode = new Mode();
        mode.addEffect(new DestroyTargetEffect(true));
        mode.addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addMode(mode);
        // or destroy target artifact.
        mode = new Mode();
        mode.addEffect(new DestroyTargetEffect());
        Target target = new TargetArtifactPermanent();
        mode.addTarget(target);
        this.getSpellAbility().addMode(mode);

    }

    public CrosissCharm(final CrosissCharm card) {
        super(card);
    }

    @Override
    public CrosissCharm copy() {
        return new CrosissCharm(this);
    }
}
