
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

/**
 *
 * @author dustinconrad
 */
public final class HighlandWeald extends CardImpl {

    public HighlandWeald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.SNOW);

        // Highland Weald enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private HighlandWeald(final HighlandWeald card) {
        super(card);
    }

    @Override
    public HighlandWeald copy() {
        return new HighlandWeald(this);
    }
}
