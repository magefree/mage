
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ShelteredThicket extends CardImpl {

    public ShelteredThicket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.FOREST);

        // <i>({T}: Add {R} or {G}.)</i>
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // Sheltered Thicket enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private ShelteredThicket(final ShelteredThicket card) {
        super(card);
    }

    @Override
    public ShelteredThicket copy() {
        return new ShelteredThicket(this);
    }
}
