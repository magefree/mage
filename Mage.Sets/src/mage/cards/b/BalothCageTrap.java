
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken2;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BalothCageTrap extends CardImpl {

    public BalothCageTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");
        this.subtype.add(SubType.TRAP);

        // If an opponent had an artifact enter the battlefield under their control this turn, you may pay {1}{G} rather than pay Baloth Cage Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{1}{G}"), BalothCageTrapCondition.instance), new PermanentsEnteredBattlefieldWatcher());

        // Create a 4/4 green Beast creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken2()));
    }

    private BalothCageTrap(final BalothCageTrap card) {
        super(card);
    }

    @Override
    public BalothCageTrap copy() {
        return new BalothCageTrap(this);
    }
}

enum BalothCageTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(opponentId);
                if (permanents != null) {
                    for (Permanent permanent : permanents) {
                        if (permanent.isArtifact(game)) {
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
        return "If an opponent had an artifact enter the battlefield under their control this turn";
    }
}
