
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class FieryConfluence extends CardImpl {

    public FieryConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);
        
        // - Fiery Confluence deals 1 damage to each creature;
        this.getSpellAbility().addEffect(new DamageAllEffect(1, new FilterCreaturePermanent()));
        
        // Fiery Confluence deals 2 damage to each opponent;
        Mode mode = new Mode(new DamagePlayersEffect(2, TargetController.OPPONENT));
        this.getSpellAbility().getModes().addMode(mode);
        
        // Destroy target artifact.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().getModes().addMode(mode);
    }

    private FieryConfluence(final FieryConfluence card) {
        super(card);
    }

    @Override
    public FieryConfluence copy() {
        return new FieryConfluence(this);
    }
}
