
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class ShivanRaptor extends CardImpl {

    public ShivanRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new EchoAbility("{2}{R}"));
    }

    private ShivanRaptor(final ShivanRaptor card) {
        super(card);
    }

    @Override
    public ShivanRaptor copy() {
        return new ShivanRaptor(this);
    }

}
