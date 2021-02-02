
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class LanternLitGraveyard extends CardImpl {

    public LanternLitGraveyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new ColorlessManaAbility());
        Ability blackManaAbility = new BlackManaAbility();
        blackManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(blackManaAbility);
        Ability redManaAbility = new RedManaAbility();
        redManaAbility.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(redManaAbility);
    }

    private LanternLitGraveyard(final LanternLitGraveyard card) {
        super(card);
    }

    @Override
    public LanternLitGraveyard copy() {
        return new LanternLitGraveyard(this);
    }
}
