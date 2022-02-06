package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 * @author LevelX2
 */
public final class ArrowVolleyTrap extends CardImpl {

    public ArrowVolleyTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");
        this.subtype.add(SubType.TRAP);

        // If four or more creatures are attacking, you may pay {1}{W} rather than pay Arrow Volley Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{1}{W}"), ArrowVolleyTrapCondition.instance));

        // Arrow Volley Trap deals 5 damage divided as you choose among any number of target attacking creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(5, StaticFilters.FILTER_ATTACKING_CREATURES));
    }

    private ArrowVolleyTrap(final ArrowVolleyTrap card) {
        super(card);
    }

    @Override
    public ArrowVolleyTrap copy() {
        return new ArrowVolleyTrap(this);
    }
}

enum ArrowVolleyTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getCombat().getAttackers().size() > 3;
    }

    @Override
    public String toString() {
        return "If four or more creatures are attacking";
    }
}
