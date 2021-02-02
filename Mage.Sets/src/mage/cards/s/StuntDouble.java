
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class StuntDouble extends CardImpl {

    public StuntDouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // You may have Stunt Double enter the battlefield as a copy of any creature on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(), true));
    }

    private StuntDouble(final StuntDouble card) {
        super(card);
    }

    @Override
    public StuntDouble copy() {
        return new StuntDouble(this);
    }
}
