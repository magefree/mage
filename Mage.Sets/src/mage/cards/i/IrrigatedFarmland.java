
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class IrrigatedFarmland extends CardImpl {

    public IrrigatedFarmland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);

        // <i>({T}: Add {W} or {U}.)</i>
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // Irrigated Farmland enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private IrrigatedFarmland(final IrrigatedFarmland card) {
        super(card);
    }

    @Override
    public IrrigatedFarmland copy() {
        return new IrrigatedFarmland(this);
    }
}
