
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PearlShard extends CardImpl {

    public PearlShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap} or {W}, {tap}: Prevent the next 2 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2),
                new OrCost(
                        "{3}, {T} or {W}, {T}", new CompositeCost(new GenericManaCost(3), new TapSourceCost(), "{3}, {T}"),
                        new CompositeCost(new ManaCostsImpl<>("{W}"), new TapSourceCost(), "{W}, {T}")
                )
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PearlShard(final PearlShard card) {
        super(card);
    }

    @Override
    public PearlShard copy() {
        return new PearlShard(this);
    }
}
