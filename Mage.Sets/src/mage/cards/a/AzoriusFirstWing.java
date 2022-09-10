package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox

 */
public final class AzoriusFirstWing extends CardImpl {

    public AzoriusFirstWing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from enchantments
        this.addAbility(ProtectionAbility.from(CardType.ENCHANTMENT));
    }

    private AzoriusFirstWing(final AzoriusFirstWing card) {
        super(card);
    }

    @Override
    public AzoriusFirstWing copy() {
        return new AzoriusFirstWing(this);
    }
}
