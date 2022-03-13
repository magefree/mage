package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimalEmpathy extends CardImpl {

    public PrimalEmpathy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{U}");

        // At the beginning of your upkeep, draw a card if you control a creature with the greatest power among creatures on the battlefield. Otherwise, put a +1/+1 counter on a creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new PrimalEmpathyEffect(), TargetController.YOU, false
        ));
    }

    private PrimalEmpathy(final PrimalEmpathy card) {
        super(card);
    }

    @Override
    public PrimalEmpathy copy() {
        return new PrimalEmpathy(this);
    }
}

class PrimalEmpathyEffect extends OneShotEffect {

    PrimalEmpathyEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card if you control a creature " +
                "with the greatest power among creatures on the battlefield. " +
                "Otherwise, put a +1/+1 counter on a creature you control";
    }

    private PrimalEmpathyEffect(final PrimalEmpathyEffect effect) {
        super(effect);
    }

    @Override
    public PrimalEmpathyEffect copy() {
        return new PrimalEmpathyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int highestPower = game
                .getBattlefield()
                .getActivePermanents(source.getControllerId(), game)
                .stream()
                .filter(permanent1 -> permanent1.isCreature(game))
                .map(Permanent::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        boolean flag = game.getBattlefield()
                .getAllActivePermanents(source.getControllerId())
                .stream()
                .filter(permanent1 -> permanent1.isCreature(game))
                .map(Permanent::getPower)
                .mapToInt(MageInt::getValue)
                .anyMatch(i -> i >= highestPower);
        if (flag) {
            return player.drawCards(1, source, game) > 0;
        }
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
    }
}