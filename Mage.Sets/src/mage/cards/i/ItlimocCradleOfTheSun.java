
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
import mage.filter.StaticFilters;

/**
 *
 * @author JRHerlehy
 */
public final class ItlimocCradleOfTheSun extends CardImpl {



    public ItlimocCradleOfTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.supertype.add(SuperType.LEGENDARY);

        // (Transforms from Growing Rites of Itlimoc.)/
        this.nightCard = true;

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {G} for each creature you control.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED)));
    }

    private ItlimocCradleOfTheSun(final ItlimocCradleOfTheSun card) {
        super(card);
    }

    @Override
    public ItlimocCradleOfTheSun copy() {
        return new ItlimocCradleOfTheSun(this);
    }
}
