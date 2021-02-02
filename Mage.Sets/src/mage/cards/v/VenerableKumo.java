

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class VenerableKumo extends CardImpl {

    public VenerableKumo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(new SoulshiftAbility(4));
    }

    private VenerableKumo(final VenerableKumo card) {
        super(card);
    }

    @Override
    public VenerableKumo copy() {
        return new VenerableKumo(this);
    }

}
