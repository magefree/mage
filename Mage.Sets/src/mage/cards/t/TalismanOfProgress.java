
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jonubuu
 */
public final class TalismanOfProgress extends CardImpl {

    public TalismanOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {W} or {U}. Talisman of Progress deals 1 damage to you.
        Ability whiteManaAbility = new WhiteManaAbility();
        whiteManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(whiteManaAbility);
        Ability blueManaAbility = new BlueManaAbility();
        blueManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blueManaAbility);
    }

    private TalismanOfProgress(final TalismanOfProgress card) {
        super(card);
    }

    @Override
    public TalismanOfProgress copy() {
        return new TalismanOfProgress(this);
    }
}
