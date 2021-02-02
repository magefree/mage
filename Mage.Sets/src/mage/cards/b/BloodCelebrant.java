
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class BloodCelebrant extends CardImpl {

    public BloodCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, Pay 1 life: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private BloodCelebrant(final BloodCelebrant card) {
        super(card);
    }

    @Override
    public BloodCelebrant copy() {
        return new BloodCelebrant(this);
    }
}
