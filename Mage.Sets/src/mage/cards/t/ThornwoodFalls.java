
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class ThornwoodFalls extends CardImpl {

    public ThornwoodFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Thornwood Falls enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Thornwood Falls enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());                
        
    }

    private ThornwoodFalls(final ThornwoodFalls card) {
        super(card);
    }

    @Override
    public ThornwoodFalls copy() {
        return new ThornwoodFalls(this);
    }
}
