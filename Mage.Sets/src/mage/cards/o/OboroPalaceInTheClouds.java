
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class OboroPalaceInTheClouds extends CardImpl {

    public OboroPalaceInTheClouds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.LEGENDARY);
        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());
        // {1}: Return Oboro, Palace in the Clouds to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new GenericManaCost(1)));
    }

    private OboroPalaceInTheClouds(final OboroPalaceInTheClouds card) {
        super(card);
    }

    @Override
    public OboroPalaceInTheClouds copy() {
        return new OboroPalaceInTheClouds(this);
    }
}
