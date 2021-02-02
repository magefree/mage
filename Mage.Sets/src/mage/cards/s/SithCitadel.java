
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Styxo
 */
public final class SithCitadel extends CardImpl {

    public SithCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Sith Citadel enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Sith Citadel enters the battlefield , add {B}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(Mana.BlackMana(1))));

        // {T}: Add {U} or {R} to you mana pool.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

    }

    private SithCitadel(final SithCitadel card) {
        super(card);
    }

    @Override
    public SithCitadel copy() {
        return new SithCitadel(this);
    }
}
