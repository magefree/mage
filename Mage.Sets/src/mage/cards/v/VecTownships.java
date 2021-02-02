
package mage.cards.v;

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
public final class VecTownships extends CardImpl {

    public VecTownships(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {G} or {W}. Vec Townships doesn't untap during your next untap step.
        Ability ability = new GreenManaAbility();
        ability.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(ability);
        ability = new WhiteManaAbility();
        ability.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(ability);
    }

    private VecTownships(final VecTownships card) {
        super(card);
    }

    @Override
    public VecTownships copy() {
        return new VecTownships(this);
    }
}
