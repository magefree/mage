
package mage.cards.y;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class YavimayaCoast extends CardImpl {

    public YavimayaCoast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // Tap: Add Green or Blue. Yavimaya Coast deals 1 damage to you.
        Ability greenManaAbility = new GreenManaAbility();
        greenManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(greenManaAbility);
        Ability blueManaAbility = new BlueManaAbility();
        blueManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blueManaAbility);
    }

    private YavimayaCoast(final YavimayaCoast card) {
        super(card);
    }

    @Override
    public YavimayaCoast copy() {
        return new YavimayaCoast(this);
    }
}
