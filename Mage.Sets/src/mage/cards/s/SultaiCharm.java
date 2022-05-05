
package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */

public final class SultaiCharm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("monocolored creature");

    static {
        filter.add(MonocoloredPredicate.instance);
    }

    public SultaiCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{G}{U}");

        // Choose one -
        // <strong>*</strong> Destroy target monocolored creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        
        // <strong>*</strong> Destroy target artifact or enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addMode(mode);
        
        // <strong>*</strong> Draw two cards, then discard a card.
        mode = new Mode(new DrawDiscardControllerEffect(2,1));
        this.getSpellAbility().addMode(mode);
    }

    private SultaiCharm(final SultaiCharm card) {
        super(card);
    }

    @Override
    public SultaiCharm copy() {
        return new SultaiCharm(this);
    }
}
