
package mage.cards.basiclands;

import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class BasicLand extends CardImpl {

    public BasicLand(UUID ownerId, CardSetInfo setInfo, ActivatedManaAbilityImpl mana) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.supertype.add(SuperType.BASIC);
        this.subtype.add(SubType.byDescription(name));
        this.addAbility(mana);
    }

    protected BasicLand(final BasicLand land) {
        super(land);
    }

}
