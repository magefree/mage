
package mage.cards.e;

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
public final class EmeraldMedallion extends CardImpl {

    private static final FilterCard filter = new FilterCard("Green spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public EmeraldMedallion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Green spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private EmeraldMedallion(final EmeraldMedallion card) {
        super(card);
    }

    @Override
    public EmeraldMedallion copy() {
        return new EmeraldMedallion(this);
    }
}
