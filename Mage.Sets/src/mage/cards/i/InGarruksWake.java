
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

/**
 * @author noxx
 */
public final class InGarruksWake extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creatures you don't control and all planeswalkers you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public InGarruksWake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{B}{B}");


        // Destroy all creatures you don't control and all planeswalkers you don't control.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private InGarruksWake(final InGarruksWake card) {
        super(card);
    }

    @Override
    public InGarruksWake copy() {
        return new InGarruksWake(this);
    }
}
