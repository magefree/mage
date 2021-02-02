
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class VineKami extends CardImpl {

    public VineKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());
        this.addAbility(new SoulshiftAbility(6));
    }

    private VineKami(final VineKami card) {
        super(card);
    }

    @Override
    public VineKami copy() {
        return new VineKami(this);
    }
}
