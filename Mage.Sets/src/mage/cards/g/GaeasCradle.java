
package mage.cards.g;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;

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
                new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent("creature you control"))
        );
        this.addAbility(ability);
    }

    public GaeasCradle(final GaeasCradle card) {
        super(card);
    }

    @Override
    public GaeasCradle copy() {
        return new GaeasCradle(this);
    }
}
