
package mage.cards.n;

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
 * @author jonubuu
 */
public final class NacatlSavage extends CardImpl {

    public NacatlSavage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from artifacts
        this.addAbility(new ProtectionAbility(new FilterArtifactCard("artifacts")));
    }

    private NacatlSavage(final NacatlSavage card) {
        super(card);
    }

    @Override
    public NacatlSavage copy() {
        return new NacatlSavage(this);
    }
}
