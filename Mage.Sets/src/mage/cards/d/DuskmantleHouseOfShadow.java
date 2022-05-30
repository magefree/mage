
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class DuskmantleHouseOfShadow extends CardImpl {

    public DuskmantleHouseOfShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {U}{B}, {tap}: Target player puts the top card of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(1), new ManaCostsImpl<>("{U}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DuskmantleHouseOfShadow(final DuskmantleHouseOfShadow card) {
        super(card);
    }

    @Override
    public DuskmantleHouseOfShadow copy() {
        return new DuskmantleHouseOfShadow(this);
    }
}
