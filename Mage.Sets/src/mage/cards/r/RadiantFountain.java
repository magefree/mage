
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author emerald000
 */
public final class RadiantFountain extends CardImpl {

    public RadiantFountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // When Radiant Fountain enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2), false));
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private RadiantFountain(final RadiantFountain card) {
        super(card);
    }

    @Override
    public RadiantFountain copy() {
        return new RadiantFountain(this);
    }
}
