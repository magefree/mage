
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author intimidatingant
 */
public final class ChaliceOfDeath extends CardImpl {

    public ChaliceOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"");

        // this card is the second face of double-faced card
        this.nightCard = true;

        // {tap}: Target player loses 5 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(5), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ChaliceOfDeath(final ChaliceOfDeath card) {
        super(card);
    }

    @Override
    public ChaliceOfDeath copy() {
        return new ChaliceOfDeath(this);
    }
}
