
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class DramaticReversal extends CardImpl {

    private final static FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanents");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public DramaticReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Untap all nonland permanents you control.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(filter));
    }

    public DramaticReversal(final DramaticReversal card) {
        super(card);
    }

    @Override
    public DramaticReversal copy() {
        return new DramaticReversal(this);
    }
}
