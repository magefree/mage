
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CanyonSlough extends CardImpl {

    public CanyonSlough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.MOUNTAIN);

        // <i>({T}: Add {B} or {R}.)</i>
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // Canyon Slough enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private CanyonSlough(final CanyonSlough card) {
        super(card);
    }

    @Override
    public CanyonSlough copy() {
        return new CanyonSlough(this);
    }
}
