

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class NezumiCutthroat extends CardImpl {

    public NezumiCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(FearAbility.getInstance());
        this.addAbility(new CantBlockAbility());
    }

    private NezumiCutthroat(final NezumiCutthroat card) {
        super(card);
    }

    @Override
    public NezumiCutthroat copy() {
        return new NezumiCutthroat(this);
    }

}
