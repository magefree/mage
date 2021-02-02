
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SlitherBlade extends CardImpl {

    public SlitherBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Slither Blade can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private SlitherBlade(final SlitherBlade card) {
        super(card);
    }

    @Override
    public SlitherBlade copy() {
        return new SlitherBlade(this);
    }
}
