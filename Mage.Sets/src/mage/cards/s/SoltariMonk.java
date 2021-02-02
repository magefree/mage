
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SoltariMonk extends CardImpl {

    public SoltariMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.SOLTARI);
        this.subtype.add(SubType.MONK);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        // Shadow
        this.addAbility(ShadowAbility.getInstance());
    }

    private SoltariMonk(final SoltariMonk card) {
        super(card);
    }

    @Override
    public SoltariMonk copy() {
        return new SoltariMonk(this);
    }
}
