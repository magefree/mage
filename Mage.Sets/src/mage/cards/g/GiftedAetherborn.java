
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class GiftedAetherborn extends CardImpl {

    public GiftedAetherborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private GiftedAetherborn(final GiftedAetherborn card) {
        super(card);
    }

    @Override
    public GiftedAetherborn copy() {
        return new GiftedAetherborn(this);
    }
}
