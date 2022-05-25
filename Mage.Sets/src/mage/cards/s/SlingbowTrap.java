
package mage.cards.s;

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
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SlingbowTrap extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SlingbowTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");
        this.subtype.add(SubType.TRAP);

        // If a black creature with flying is attacking, you may pay {G} rather than pay Slingbow Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{G}"), SlingbowTrapCondition.instance));

        // Destroy target attacking creature with flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private SlingbowTrap(final SlingbowTrap card) {
        super(card);
    }

    @Override
    public SlingbowTrap copy() {
        return new SlingbowTrap(this);
    }
}

enum SlingbowTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID attackingCreatureId : game.getCombat().getAttackers()) {
            Permanent attackingCreature = game.getPermanent(attackingCreatureId);
            if (attackingCreature != null) {
                if (attackingCreature.getColor(game).isBlack() && attackingCreature.hasAbility(FlyingAbility.getInstance(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If a black creature with flying is attacking";
    }
}
