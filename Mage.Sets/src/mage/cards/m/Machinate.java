
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Alexsandr0x
 */
public final class Machinate extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts you control");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public Machinate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Look at the top X cards of your library, where X is the number of artifacts you control. Put one of those cards into your hand and the rest on the bottom of your library in any order.
        DynamicValue artifactsOnControl = new PermanentsOnBattlefieldCount(filter);
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(artifactsOnControl, false, new StaticValue(1), new FilterCard(), Zone.LIBRARY, false, false));
    }

    public Machinate(final Machinate card) {
        super(card);
    }

    @Override
    public Machinate copy() {
        return new Machinate(this);
    }
}
