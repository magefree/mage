

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactCard;

/**
 *
 * @author ayratn
 */
public final class TelJiladFallen extends CardImpl {

    public TelJiladFallen (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new ProtectionAbility(new FilterArtifactCard("artifacts")));
    }

    private TelJiladFallen(final TelJiladFallen card) {
        super(card);
    }

    @Override
    public TelJiladFallen copy() {
        return new TelJiladFallen(this);
    }

}
