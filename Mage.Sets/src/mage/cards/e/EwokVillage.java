
package mage.cards.e;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Styxo
 */
public final class EwokVillage extends CardImpl {

    public EwokVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Ewok Village enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Ewok Village enters the battlefield , add {G}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(Mana.GreenMana(1))));

        // {T}: Add {R} or {W} to you mana pool.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private EwokVillage(final EwokVillage card) {
        super(card);
    }

    @Override
    public EwokVillage copy() {
        return new EwokVillage(this);
    }
}
