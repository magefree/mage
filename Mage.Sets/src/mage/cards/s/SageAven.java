
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SageAven extends CardImpl {

    public SageAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Sage Aven enters the battlefield, look at the top four cards of your library, then put them back in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryControllerEffect(4)));
    }

    private SageAven(final SageAven card) {
        super(card);
    }

    @Override
    public SageAven copy() {
        return new SageAven(this);
    }
}
