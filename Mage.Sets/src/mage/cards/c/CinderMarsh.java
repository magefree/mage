
package mage.cards.c;

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
public final class CinderMarsh extends CardImpl {

    public CinderMarsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {B} or {R}. Cinder Marsh doesn't untap during your next untap step.
        Ability ability = new BlackManaAbility();
        ability.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(ability);
        ability = new RedManaAbility();
        ability.addEffect(new DontUntapInControllersNextUntapStepSourceEffect());
        this.addAbility(ability);
    }

    private CinderMarsh(final CinderMarsh card) {
        super(card);
    }

    @Override
    public CinderMarsh copy() {
        return new CinderMarsh(this);
    }
}
