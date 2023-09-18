
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward
 */
public final class UnhallowedCathar extends CardImpl {

    public UnhallowedCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.color.setBlack(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Unhallowed Cathar can't block.
        this.addAbility(new CantBlockAbility());
    }

    private UnhallowedCathar(final UnhallowedCathar card) {
        super(card);
    }

    @Override
    public UnhallowedCathar copy() {
        return new UnhallowedCathar(this);
    }
}
