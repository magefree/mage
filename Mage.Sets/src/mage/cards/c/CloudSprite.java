
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
 * @author Loki
 */
public final class CloudSprite extends CardImpl {

    public CloudSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.FAERIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        // Cloud Sprite can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private CloudSprite(final CloudSprite card) {
        super(card);
    }

    @Override
    public CloudSprite copy() {
        return new CloudSprite(this);
    }
}
