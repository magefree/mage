
package mage.cards.b;

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
public final class Brushland extends CardImpl {

    public Brushland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new ColorlessManaAbility());

        Ability greenManaAbility = new GreenManaAbility();
        greenManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(greenManaAbility);
        Ability whiteManaAbility = new WhiteManaAbility();
        whiteManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(whiteManaAbility);
    }

    private Brushland(final Brushland card) {
        super(card);
    }

    @Override
    public Brushland copy() {
        return new Brushland(this);
    }
}
