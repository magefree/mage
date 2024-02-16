

package mage.cards.n;

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
public final class NezumiRonin extends CardImpl {

    public NezumiRonin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.addAbility(new BushidoAbility(1));
    }

    private NezumiRonin(final NezumiRonin card) {
        super(card);
    }

    @Override
    public NezumiRonin copy() {
        return new NezumiRonin(this);
    }

}
