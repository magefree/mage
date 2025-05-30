
package mage.cards.c;

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
public final class CavesOfKoilos extends CardImpl {

    public CavesOfKoilos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");


        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // Tap: Add White or Black. Caves of Koilos deals 1 damage to you.
        Ability whiteManaAbility = new WhiteManaAbility();
        whiteManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(whiteManaAbility);
        Ability blackManaAbility = new BlackManaAbility();
        blackManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blackManaAbility);
    }

    private CavesOfKoilos(final CavesOfKoilos card) {
        super(card);
    }

    @Override
    public CavesOfKoilos copy() {
        return new CavesOfKoilos(this);
    }
}
