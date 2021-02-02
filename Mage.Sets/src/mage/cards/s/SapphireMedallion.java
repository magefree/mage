
package mage.cards.s;

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
public final class SapphireMedallion extends CardImpl {

    private static final FilterCard filter = new FilterCard("Blue spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public SapphireMedallion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Blue spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private SapphireMedallion(final SapphireMedallion card) {
        super(card);
    }

    @Override
    public SapphireMedallion copy() {
        return new SapphireMedallion(this);
    }
}
