
package mage.cards.basiclands;

import java.util.UUID;

import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.constants.SubType;
import mage.constants.SuperType;

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

    public BasicLand(BasicLand land) {
        super(land);
    }

}
