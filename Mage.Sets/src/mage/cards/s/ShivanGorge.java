
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ShivanGorge extends CardImpl {

    public ShivanGorge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        addSuperType(SuperType.LEGENDARY);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}{R}, {tap}: Shivan Gorge deals 1 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ShivanGorge(final ShivanGorge card) {
        super(card);
    }

    @Override
    public ShivanGorge copy() {
        return new ShivanGorge(this);
    }
}
