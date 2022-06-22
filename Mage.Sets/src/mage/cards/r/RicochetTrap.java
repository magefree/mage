package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RicochetTrap extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with a single target");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public RicochetTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.subtype.add(SubType.TRAP);

        // If an opponent cast a blue spell this turn, you may pay {R} rather than pay Ricochet Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{R}"), RicochetTrapCondition.instance), new SpellsCastWatcher());

        // Change the target of target spell with a single target.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private RicochetTrap(final RicochetTrap card) {
        super(card);
    }

    @Override
    public RicochetTrap copy() {
        return new RicochetTrap(this);
    }
}

enum RicochetTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(opponentId);
                if (spells != null) {
                    for (Spell spell : spells) {
                        if (spell.getColor(game).isBlue()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent cast a blue spell this turn";
    }
}
