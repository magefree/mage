
package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Styxo
 */
public final class DroidFoundry extends CardImpl {

    public DroidFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Droid Foundry enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Droid Foundry enters the battlefield , add {U}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(Mana.BlueMana(1))));

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private DroidFoundry(final DroidFoundry card) {
        super(card);
    }

    @Override
    public DroidFoundry copy() {
        return new DroidFoundry(this);
    }
}
