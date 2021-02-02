
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SoulshiftAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ToriiWatchward extends CardImpl {

    public ToriiWatchward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(new SoulshiftAbility(4));
    }

    private ToriiWatchward(final ToriiWatchward card) {
        super(card);
    }

    @Override
    public ToriiWatchward copy() {
        return new ToriiWatchward(this);
    }
}
