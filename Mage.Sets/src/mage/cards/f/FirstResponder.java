package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirstResponder extends CardImpl {

    public FirstResponder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, you may return another creature you control to its owner's hand, then put a number of +1/+1 counters equal to that creature's power on First Responder.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new FirstResponderEffect(), TargetController.YOU, false
        ));
    }

    private FirstResponder(final FirstResponder card) {
        super(card);
    }

    @Override
    public FirstResponder copy() {
        return new FirstResponder(this);
    }
}

class FirstResponderEffect extends OneShotEffect {

    FirstResponderEffect() {
        super(Outcome.Benefit);
        staticText = "you may return another creature you control to its owner's hand, " +
                "then put a number of +1/+1 counters equal to that creature's power on {this}";
    }

    private FirstResponderEffect(final FirstResponderEffect effect) {
        super(effect);
    }

    @Override
    public FirstResponderEffect copy() {
        return new FirstResponderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        );
        target.setNotTarget(true);
        player.choose(Outcome.ReturnToHand, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.HAND, source, game);
        int power = permanent.getPower().getValue();
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (power < 1 || sourcePermanent == null) {
            sourcePermanent.addCounters(CounterType.P1P1.createInstance(power), source, game);
        }
        return true;
    }
}
