

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DevotedRetainer extends CardImpl {

    public DevotedRetainer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BushidoAbility(1));
    }

    private DevotedRetainer(final DevotedRetainer card) {
        super(card);
    }

    @Override
    public DevotedRetainer copy() {
        return new DevotedRetainer(this);
    }

}
