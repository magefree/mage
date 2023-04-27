
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ScatteredGroves extends CardImpl {

    public ScatteredGroves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.PLAINS);

        // <i>({T}: Add {G} or {W}.)</i>
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Scattered Groves enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private ScatteredGroves(final ScatteredGroves card) {
        super(card);
    }

    @Override
    public ScatteredGroves copy() {
        return new ScatteredGroves(this);
    }
}
