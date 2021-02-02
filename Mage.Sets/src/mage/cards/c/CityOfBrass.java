
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jeffwadsworth
 */
public final class CityOfBrass extends CardImpl {

    public CityOfBrass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Whenever City of Brass becomes tapped, it deals 1 damage to you.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DamageControllerEffect(1, "it")));

        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private CityOfBrass(final CityOfBrass card) {
        super(card);
    }

    @Override
    public CityOfBrass copy() {
        return new CityOfBrass(this);
    }
}
