
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class MossfireValley extends CardImpl {

    public MossfireValley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {1}, {tap}: Add {R}{G}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 0, 1, 1, 0, 0, 0), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MossfireValley(final MossfireValley card) {
        super(card);
    }

    @Override
    public MossfireValley copy() {
        return new MossfireValley(this);
    }
}
