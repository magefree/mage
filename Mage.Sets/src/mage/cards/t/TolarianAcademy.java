

package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Backfir3
 */

public final class TolarianAcademy extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public TolarianAcademy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        addSuperType(SuperType.LEGENDARY);

        DynamicManaAbility ability = new DynamicManaAbility(Mana.BlueMana(1), new PermanentsOnBattlefieldCount(filter));
        this.addAbility(ability);
    }

    public TolarianAcademy(final TolarianAcademy card) {
        super(card);
    }

    @Override
    public TolarianAcademy copy() {
        return new TolarianAcademy(this);
    }
}
