
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class PermafrostTrap extends CardImpl {

    public PermafrostTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");
        this.subtype.add(SubType.TRAP);

        // If an opponent had a green creature enter the battlefield under their control this turn, you may pay {U} rather than pay Permafrost Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{U}"), PermafrostTrapCondition.instance), new PermanentsEnteredBattlefieldWatcher());

        // Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("Those creatures"));
    }

    private PermafrostTrap(final PermafrostTrap card) {
        super(card);
    }

    @Override
    public PermafrostTrap copy() {
        return new PermafrostTrap(this);
    }
}

enum PermafrostTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(opponentId);
                if (permanents != null) {
                    for (Permanent permanent : permanents) {
                        if (permanent.isCreature(game) && permanent.getColor(game).isGreen()) {
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
        return "If an opponent had a green creature enter the battlefield under their control this turn";
    }
}
