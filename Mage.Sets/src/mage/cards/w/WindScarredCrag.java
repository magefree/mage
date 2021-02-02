
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class WindScarredCrag extends CardImpl {

    public WindScarredCrag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Wind-Scarred Crag enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Wind-Scarred Crag enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());                
        
    }

    private WindScarredCrag(final WindScarredCrag card) {
        super(card);
    }

    @Override
    public WindScarredCrag copy() {
        return new WindScarredCrag(this);
    }
}
