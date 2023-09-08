

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SmolderingButcher extends CardImpl {

    public SmolderingButcher (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
        this.addAbility(WitherAbility.getInstance());
    }

    private SmolderingButcher(final SmolderingButcher card) {
        super(card);
    }

    @Override
    public SmolderingButcher copy() {
        return new SmolderingButcher(this);
    }

}
