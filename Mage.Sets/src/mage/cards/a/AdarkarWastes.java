
package mage.cards.a;

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
public final class AdarkarWastes extends CardImpl {

    public AdarkarWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());

        Ability whiteManaAbility = new WhiteManaAbility();
        whiteManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(whiteManaAbility);
        Ability blueManaAbility = new BlueManaAbility();
        blueManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blueManaAbility);
    }

    private AdarkarWastes(final AdarkarWastes card) {
        super(card);
    }

    @Override
    public AdarkarWastes copy() {
        return new AdarkarWastes(this);
    }
}
