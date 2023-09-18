package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author weirddan455
 */
public final class ErtaisScorn extends CardImpl {

    public ErtaisScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // This spell costs {U} less to cast if an opponent cast two or more spells this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(
                        new ManaCostsImpl<>("{U}"),
                        ErtaisScornCondition.instance
                )
        ).setRuleAtTheTop(true), new CastSpellLastTurnWatcher());

        // Counter target spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private ErtaisScorn(final ErtaisScorn card) {
        super(card);
    }

    @Override
    public ErtaisScorn copy() {
        return new ErtaisScorn(this);
    }
}

enum ErtaisScornCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(opponentId) >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent cast two or more spells this turn";
    }
}
