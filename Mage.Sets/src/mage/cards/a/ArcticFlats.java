
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

/**
 *
 * @author dustinconrad
 */
public final class ArcticFlats extends CardImpl {

    public ArcticFlats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.SNOW);

        // Arctic Flats enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private ArcticFlats(final ArcticFlats card) {
        super(card);
    }

    @Override
    public ArcticFlats copy() {
        return new ArcticFlats(this);
    }
}
