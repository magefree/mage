
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author Loki
 */
public final class CloudcrestLake extends CardImpl {

    public CloudcrestLake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        
        // {T}: Add {C}.
        // {T}: Add {W} or {U}. Cloudcrest Lake doesn't untap during your next untap step.
        this.addAbility(new ColorlessManaAbility());
        Ability whiteManaAbility = new WhiteManaAbility();
        whiteManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(whiteManaAbility);
        Ability blueManaAbility = new BlueManaAbility();
        blueManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(blueManaAbility);
    }

    private CloudcrestLake(final CloudcrestLake card) {
        super(card);
    }

    @Override
    public CloudcrestLake copy() {
        return new CloudcrestLake(this);
    }
}
