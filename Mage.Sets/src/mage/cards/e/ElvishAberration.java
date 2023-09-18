
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class ElvishAberration extends CardImpl {

    public ElvishAberration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {tap}: Add {G}{G}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(3), new TapSourceCost()));
        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ElvishAberration(final ElvishAberration card) {
        super(card);
    }

    @Override
    public ElvishAberration copy() {
        return new ElvishAberration(this);
    }
}
