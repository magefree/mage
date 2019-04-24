
package mage.cards.i;

import java.util.UUID;

import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author JRHerlehy
 */
public final class ItlimocCradleOfTheSun extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("creature you control");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public ItlimocCradleOfTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.addSuperType(SuperType.LEGENDARY);

        // (Transforms from Growing Rites of Itlimoc.)/
        this.nightCard = true;

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {G} for each creature you control.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), new PermanentsOnBattlefieldCount(filter)));
    }

    public ItlimocCradleOfTheSun(final ItlimocCradleOfTheSun card) {
        super(card);
    }

    @Override
    public ItlimocCradleOfTheSun copy() {
        return new ItlimocCradleOfTheSun(this);
    }
}
