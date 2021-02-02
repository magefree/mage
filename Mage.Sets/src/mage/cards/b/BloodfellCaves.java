
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class BloodfellCaves extends CardImpl {

    public BloodfellCaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Bloodfell Caves enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // When Bloodfell Caves enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        
        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());           
    }

    private BloodfellCaves(final BloodfellCaves card) {
        super(card);
    }

    @Override
    public BloodfellCaves copy() {
        return new BloodfellCaves(this);
    }
}
