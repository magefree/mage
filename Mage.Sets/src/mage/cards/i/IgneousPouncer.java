
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MountaincyclingAbility;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class IgneousPouncer extends CardImpl {

    public IgneousPouncer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}");
        this.subtype.add(SubType.ELEMENTAL);


        
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Swampcycling {2}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{2}")));
        // Mountaincycling {2}
        this.addAbility(new MountaincyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private IgneousPouncer(final IgneousPouncer card) {
        super(card);
    }

    @Override
    public IgneousPouncer copy() {
        return new IgneousPouncer(this);
    }
}
