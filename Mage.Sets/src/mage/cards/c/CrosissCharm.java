package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CrosissCharm extends CardImpl {

    public CrosissCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{B}{R}");

        // Choose one - Return target permanent to its owner's hand;
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        // or destroy target nonblack creature, and it can't be regenerated;
        Mode mode = new Mode();
        mode.addEffect(new DestroyTargetEffect(true));
        mode.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addMode(mode);
        // or destroy target artifact.
        mode = new Mode();
        mode.addEffect(new DestroyTargetEffect());
        Target target = new TargetArtifactPermanent();
        mode.addTarget(target);
        this.getSpellAbility().addMode(mode);

    }

    private CrosissCharm(final CrosissCharm card) {
        super(card);
    }

    @Override
    public CrosissCharm copy() {
        return new CrosissCharm(this);
    }
}
