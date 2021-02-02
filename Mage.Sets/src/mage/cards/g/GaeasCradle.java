
package mage.cards.g;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author Backfir3
 */
public final class GaeasCradle extends CardImpl {

    public GaeasCradle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // {T}: Add {G} for each creature you control.
        DynamicManaAbility ability = new DynamicManaAbility(
                Mana.GreenMana(1),
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED)
        );
        this.addAbility(ability);
    }

    private GaeasCradle(final GaeasCradle card) {
        super(card);
    }

    @Override
    public GaeasCradle copy() {
        return new GaeasCradle(this);
    }
}
