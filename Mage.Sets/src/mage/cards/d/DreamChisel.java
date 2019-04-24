
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.other.FaceDownPredicate;

/**
 *
 * @author North
 */
public final class DreamChisel extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Face-down creature spells");

    static {
        filter.add(new FaceDownPredicate());
    }

    public DreamChisel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Face-down creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    public DreamChisel(final DreamChisel card) {
        super(card);
    }

    @Override
    public DreamChisel copy() {
        return new DreamChisel(this);
    }
}
