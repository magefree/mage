
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class TranquilGarden extends CardImpl {

    public TranquilGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new ColorlessManaAbility());
        Ability greenManaAbility = new GreenManaAbility();
        greenManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(greenManaAbility);
        Ability whiteManaAbility = new WhiteManaAbility();
        whiteManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(whiteManaAbility);
    }

    private TranquilGarden(final TranquilGarden card) {
        super(card);
    }

    @Override
    public TranquilGarden copy() {
        return new TranquilGarden(this);
    }
}
