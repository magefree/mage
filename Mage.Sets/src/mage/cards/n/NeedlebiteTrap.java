
package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NeedlebiteTrap extends CardImpl {

    public NeedlebiteTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{B}{B}");
        this.subtype.add(SubType.TRAP);

        // If an opponent gained life this turn, you may pay {B} rather than pay Needlebite Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{B}"), NeedlebiteTrapCondition.instance), new PlayerGainedLifeWatcher());

        // Target player loses 5 life and you gain 5 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(5));
        this.getSpellAbility().addEffect(new GainLifeEffect(5).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private NeedlebiteTrap(final NeedlebiteTrap card) {
        super(card);
    }

    @Override
    public NeedlebiteTrap copy() {
        return new NeedlebiteTrap(this);
    }
}

enum NeedlebiteTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                if (watcher.getLifeGained(opponentId) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent gained life this turn";
    }
}
