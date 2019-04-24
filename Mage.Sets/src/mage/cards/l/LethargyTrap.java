
package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class LethargyTrap extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creatures");

    static {
        filter.add(new AttackingPredicate());
    }

    public LethargyTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");
        this.subtype.add(SubType.TRAP);

        // If three or more creatures are attacking, you may pay {U} rather than pay Lethargy Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{U}"), LethargyTrapCondition.instance));

        // Attacking creatures get -3/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-3, 0, Duration.EndOfTurn, filter, false));

    }

    public LethargyTrap(final LethargyTrap card) {
        super(card);
    }

    @Override
    public LethargyTrap copy() {
        return new LethargyTrap(this);
    }
}

enum LethargyTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getCombat().getAttackers().size() > 2;
    }

    @Override
    public String toString() {
        return "If three or more creatures are attacking";
    }
}
