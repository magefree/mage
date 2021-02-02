

package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Backfir3
 */

public final class SerrasSanctum extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("enchantment you control");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public SerrasSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        addSuperType(SuperType.LEGENDARY);

        DynamicManaAbility ability = new DynamicManaAbility(Mana.WhiteMana(1), new PermanentsOnBattlefieldCount(filter));
        this.addAbility(ability);
    }

    private SerrasSanctum(final SerrasSanctum card) {
        super(card);
    }

    @Override
    public SerrasSanctum copy() {
        return new SerrasSanctum(this);
    }
}
