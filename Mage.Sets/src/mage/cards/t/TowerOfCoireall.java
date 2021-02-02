
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class TowerOfCoireall extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Walls");
    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public TowerOfCoireall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}: Target creature can't be blocked by Walls this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByAllTargetEffect(filter, Duration.EndOfTurn).setText("Target creature can't be blocked by Walls this turn"), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TowerOfCoireall(final TowerOfCoireall card) {
        super(card);
    }

    @Override
    public TowerOfCoireall copy() {
        return new TowerOfCoireall(this);
    }
}
