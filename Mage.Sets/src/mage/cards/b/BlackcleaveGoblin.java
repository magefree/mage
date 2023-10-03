

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BlackcleaveGoblin extends CardImpl {

    public BlackcleaveGoblin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.PHYREXIAN, SubType.GOBLIN, SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
    }

    private BlackcleaveGoblin(final BlackcleaveGoblin card) {
        super(card);
    }

    @Override
    public BlackcleaveGoblin copy() {
        return new BlackcleaveGoblin(this);
    }

}
