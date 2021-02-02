
package mage.cards.r;

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
public final class RubyMedallion extends CardImpl {

    private static final FilterCard filter = new FilterCard("Red spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public RubyMedallion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Red spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private RubyMedallion(final RubyMedallion card) {
        super(card);
    }

    @Override
    public RubyMedallion copy() {
        return new RubyMedallion(this);
    }
}
