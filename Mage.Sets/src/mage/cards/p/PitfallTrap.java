
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class PitfallTrap extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public PitfallTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.subtype.add(SubType.TRAP);

        // If exactly one creature is attacking, you may pay {W} rather than pay Pitfall Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{W}"), PitfallTrapCondition.instance));

        // Destroy target attacking creature without flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private PitfallTrap(final PitfallTrap card) {
        super(card);
    }

    @Override
    public PitfallTrap copy() {
        return new PitfallTrap(this);
    }
}

enum PitfallTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getCombat().getAttackers().size() == 1;
    }

    @Override
    public String toString() {
        return "If exactly one creature is attacking";
    }
}
