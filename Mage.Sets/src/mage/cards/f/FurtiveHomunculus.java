
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class FurtiveHomunculus extends CardImpl {

    public FurtiveHomunculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Skulk (This creature can't be blocked by creatures with greater power.)
        this.addAbility(new SkulkAbility());
    }

    private FurtiveHomunculus(final FurtiveHomunculus card) {
        super(card);
    }

    @Override
    public FurtiveHomunculus copy() {
        return new FurtiveHomunculus(this);
    }
}
