

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BladetuskBoar extends CardImpl {

    public BladetuskBoar (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(IntimidateAbility.getInstance());
    }

    private BladetuskBoar(final BladetuskBoar card) {
        super(card);
    }

    @Override
    public BladetuskBoar copy() {
        return new BladetuskBoar(this);
    }

}
