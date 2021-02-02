
package mage.cards.h;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Styxo
 */
public final class HuttPalace extends CardImpl {

    public HuttPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Hutt Palace enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Hutt Palace enters the battlefield , add {R}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(Mana.RedMana(1))));

        // {T}: Add {B} or {G} to you mana pool.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private HuttPalace(final HuttPalace card) {
        super(card);
    }

    @Override
    public HuttPalace copy() {
        return new HuttPalace(this);
    }
}
