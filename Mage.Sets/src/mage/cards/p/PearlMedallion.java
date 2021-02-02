
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author noxx
 */
public final class PearlMedallion extends CardImpl {

    private static final FilterCard filter = new FilterCard("White spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public PearlMedallion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // White spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private PearlMedallion(final PearlMedallion card) {
        super(card);
    }

    @Override
    public PearlMedallion copy() {
        return new PearlMedallion(this);
    }
}
