
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class JhessianZombies extends CardImpl {

    public JhessianZombies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);


        
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // Islandcycling {2}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
        // Swampcycling {2}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private JhessianZombies(final JhessianZombies card) {
        super(card);
    }

    @Override
    public JhessianZombies copy() {
        return new JhessianZombies(this);
    }
}
