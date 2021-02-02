
package mage.cards.n;

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
public final class NaturesRuin extends CardImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("green creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public NaturesRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Destroy all green creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private NaturesRuin(final NaturesRuin card) {
        super(card);
    }

    @Override
    public NaturesRuin copy() {
        return new NaturesRuin(this);
    }
}
