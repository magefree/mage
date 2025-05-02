

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class MossKami extends CardImpl {

    public MossKami (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(TrampleAbility.getInstance());
    }

    private MossKami(final MossKami card) {
        super(card);
    }

    @Override
    public MossKami copy() {
        return new MossKami(this);
    }

}
