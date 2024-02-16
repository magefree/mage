
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class FetidPools extends CardImpl {

    public FetidPools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.SWAMP);

        // <i>({T}: Add {U} or {B}.)</i>
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // Fetid Pools enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private FetidPools(final FetidPools card) {
        super(card);
    }

    @Override
    public FetidPools copy() {
        return new FetidPools(this);
    }
}
