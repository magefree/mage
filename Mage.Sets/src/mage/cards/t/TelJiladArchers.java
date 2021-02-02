
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactCard;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class TelJiladArchers extends CardImpl {

    public TelJiladArchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Protection from artifacts; reach
        this.addAbility(new ProtectionAbility(new FilterArtifactCard("artifacts")));
        this.addAbility(ReachAbility.getInstance());
        
    }

    private TelJiladArchers(final TelJiladArchers card) {
        super(card);
    }

    @Override
    public TelJiladArchers copy() {
        return new TelJiladArchers(this);
    }
}
