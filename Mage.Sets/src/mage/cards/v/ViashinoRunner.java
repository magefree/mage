
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ViashinoRunner extends CardImpl {

    public ViashinoRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.VIASHINO);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());
    }

    private ViashinoRunner(final ViashinoRunner card) {
        super(card);
    }

    @Override
    public ViashinoRunner copy() {
        return new ViashinoRunner(this);
    }
}
