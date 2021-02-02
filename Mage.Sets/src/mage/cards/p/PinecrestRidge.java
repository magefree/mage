
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class PinecrestRidge extends CardImpl {

    public PinecrestRidge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new ColorlessManaAbility());
        Ability redManaAbility = new RedManaAbility();
        redManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(redManaAbility);
        Ability greenManaAbility = new GreenManaAbility();
        greenManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(greenManaAbility);
    }

    private PinecrestRidge(final PinecrestRidge card) {
        super(card);
    }

    @Override
    public PinecrestRidge copy() {
        return new PinecrestRidge(this);
    }
}
