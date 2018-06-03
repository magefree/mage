
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class PearlShard extends CardImpl {

    public PearlShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}, {tap} or {W}, {tap}: Prevent the next 2 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ManaCostsImpl("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public PearlShard(final PearlShard card) {
        super(card);
    }

    @Override
    public PearlShard copy() {
        return new PearlShard(this);
    }
}
