
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
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
 * @author jeffwadsworth
 */
public final class WhiplashTrap extends CardImpl {

    public WhiplashTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");
        this.subtype.add(SubType.TRAP);

        // If an opponent had two or more creatures enter the battlefield under their control this turn, you may pay {U} rather than pay Whiplash Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{U}"), WhiplashTrapCondition.instance), new PermanentsEnteredBattlefieldWatcher());

        // Return two target creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setText("return two target creatures to their owners' hands"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));

    }

    private WhiplashTrap(final WhiplashTrap card) {
        super(card);
    }

    @Override
    public WhiplashTrap copy() {
        return new WhiplashTrap(this);
    }
}

enum WhiplashTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(opponentId);
                if (permanents != null) {
                    int count = 0;
                    for (Permanent permanent : permanents) {
                        if (permanent.isCreature(game)) {
                            count++;
                            if (count == 2) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent had two or more creatures enter the battlefield under their control this turn";
    }
}
