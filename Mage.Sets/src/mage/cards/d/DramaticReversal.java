
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class DramaticReversal extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanents");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DramaticReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Untap all nonland permanents you control.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(filter));
    }

    private DramaticReversal(final DramaticReversal card) {
        super(card);
    }

    @Override
    public DramaticReversal copy() {
        return new DramaticReversal(this);
    }
}
