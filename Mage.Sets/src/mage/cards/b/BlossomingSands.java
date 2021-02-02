
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class BlossomingSands extends CardImpl {

    public BlossomingSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Blossoming Sands enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // When Blossoming Sands enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());           
    }

    private BlossomingSands(final BlossomingSands card) {
        super(card);
    }

    @Override
    public BlossomingSands copy() {
        return new BlossomingSands(this);
    }
}
