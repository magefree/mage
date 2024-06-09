

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class RoninHoundmaster extends CardImpl {

    public RoninHoundmaster (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new BushidoAbility(1));
    }

    private RoninHoundmaster(final RoninHoundmaster card) {
        super(card);
    }

    @Override
    public RoninHoundmaster copy() {
        return new RoninHoundmaster(this);
    }

}
