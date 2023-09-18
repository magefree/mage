
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author Rafbill
 */
public final class MindbreakTrap extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spells");

    public MindbreakTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.subtype.add(SubType.TRAP);

        // If an opponent cast three or more spells this turn, you may pay {0} rather than pay Mindbreak Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new GenericManaCost(0), MindbreakTrapCondition.instance));

        // Exile any number of target spells.
        this.getSpellAbility().addTarget(new TargetSpell(0, Integer.MAX_VALUE, filter));
        this.getSpellAbility().addEffect(new ExileTargetEffect("Exile any number of target spells"));
    }

    private MindbreakTrap(final MindbreakTrap card) {
        super(card);
    }

    @Override
    public MindbreakTrap copy() {
        return new MindbreakTrap(this);
    }
}

enum MindbreakTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(opponentId) > 2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent cast three or more spells this turn";
    }

}
