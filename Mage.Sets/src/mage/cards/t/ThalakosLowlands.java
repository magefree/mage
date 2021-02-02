
package mage.cards.t;

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
 *
 * @author Loki
 */
public final class ThalakosLowlands extends CardImpl {

    public ThalakosLowlands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {W} or {U}. Thalakos Lowlands doesn't untap during your next untap step.
        Ability ability = new WhiteManaAbility();
        ability.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(ability);
        ability = new BlueManaAbility();
        ability.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(ability);
    }

    private ThalakosLowlands(final ThalakosLowlands card) {
        super(card);
    }

    @Override
    public ThalakosLowlands copy() {
        return new ThalakosLowlands(this);
    }
}
