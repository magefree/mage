
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DruidOfTheCowl extends CardImpl {

    public DruidOfTheCowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private DruidOfTheCowl(final DruidOfTheCowl card) {
        super(card);
    }

    @Override
    public DruidOfTheCowl copy() {
        return new DruidOfTheCowl(this);
    }
}
