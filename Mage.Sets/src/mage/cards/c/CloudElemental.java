
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CloudElemental extends CardImpl {

    public CloudElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        // Cloud Elemental can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private CloudElemental(final CloudElemental card) {
        super(card);
    }

    @Override
    public CloudElemental copy() {
        return new CloudElemental(this);
    }
}