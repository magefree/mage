
package mage.cards.y;

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
 * @author Plopman
 */
public final class YavimayaScion extends CardImpl {

    public YavimayaScion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Protection from artifacts
        this.addAbility(new ProtectionAbility(new FilterArtifactCard("artifacts")));
    }

    private YavimayaScion(final YavimayaScion card) {
        super(card);
    }

    @Override
    public YavimayaScion copy() {
        return new YavimayaScion(this);
    }
}
