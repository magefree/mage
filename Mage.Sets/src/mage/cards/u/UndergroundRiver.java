
package mage.cards.u;

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
public final class UndergroundRiver extends CardImpl {

    public UndergroundRiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());

        Ability blueManaAbility = new BlueManaAbility();
        blueManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blueManaAbility);
        Ability blackManaAbility = new BlackManaAbility();
        blackManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blackManaAbility);
    }

    private UndergroundRiver(final UndergroundRiver card) {
        super(card);
    }

    @Override
    public UndergroundRiver copy() {
        return new UndergroundRiver(this);
    }
}
