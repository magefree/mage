
package mage.cards.v;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author dustinconrad
 */
public final class VirtuesRuin extends CardImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("white creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public VirtuesRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Destroy all white creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private VirtuesRuin(final VirtuesRuin card) {
        super(card);
    }

    @Override
    public VirtuesRuin copy() {
        return new VirtuesRuin(this);
    }
}
