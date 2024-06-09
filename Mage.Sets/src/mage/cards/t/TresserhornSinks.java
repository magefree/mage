
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

/**
 *
 * @author dustinconrad
 */
public final class TresserhornSinks extends CardImpl {

    public TresserhornSinks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.SNOW);

        // Tresserhorn Sinks enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private TresserhornSinks(final TresserhornSinks card) {
        super(card);
    }

    @Override
    public TresserhornSinks copy() {
        return new TresserhornSinks(this);
    }
}
