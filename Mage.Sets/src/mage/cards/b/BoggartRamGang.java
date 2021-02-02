
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BoggartRamGang extends CardImpl {

    public BoggartRamGang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/G}{R/G}{R/G}");
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(HasteAbility.getInstance());
        this.addAbility(WitherAbility.getInstance());
    }

    private BoggartRamGang(final BoggartRamGang card) {
        super(card);
    }

    @Override
    public BoggartRamGang copy() {
        return new BoggartRamGang(this);
    }
}
