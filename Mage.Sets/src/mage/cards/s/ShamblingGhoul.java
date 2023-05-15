
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.TapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ShamblingGhoul extends CardImpl {
    
    private static final String STATIC_TEXT = "tapped";

    public ShamblingGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Shambling Ghoul enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldAbility(new TapSourceEffect(), STATIC_TEXT));
    }

    private ShamblingGhoul(final ShamblingGhoul card) {
        super(card);
    }

    @Override
    public ShamblingGhoul copy() {
        return new ShamblingGhoul(this);
    }
}
