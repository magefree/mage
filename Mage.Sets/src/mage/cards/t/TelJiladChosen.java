
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactCard;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class TelJiladChosen extends CardImpl {

    public TelJiladChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from artifacts
        this.addAbility(new ProtectionAbility(new FilterArtifactCard("artifacts")));
    }

    private TelJiladChosen(final TelJiladChosen card) {
        super(card);
    }

    @Override
    public TelJiladChosen copy() {
        return new TelJiladChosen(this);
    }
}
