

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class TormentedSoul extends CardImpl {

    public TormentedSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new CantBlockAbility());
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    public TormentedSoul(final TormentedSoul card) {
        super(card);
    }

    @Override
    public TormentedSoul copy() {
        return new TormentedSoul(this);
    }

}
