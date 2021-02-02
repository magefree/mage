
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class WaterveilCavern extends CardImpl {

    public WaterveilCavern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // Tap: Add Blue or Black. Waterveil Cavern doesn't untap during your next untap step.
        Ability blueManaAbility = new BlueManaAbility();
        blueManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(blueManaAbility);
        Ability blackManaAbility = new BlackManaAbility();
        blackManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(blackManaAbility);
    }

    private WaterveilCavern(final WaterveilCavern card) {
        super(card);
    }

    @Override
    public WaterveilCavern copy() {
        return new WaterveilCavern(this);
    }
}
