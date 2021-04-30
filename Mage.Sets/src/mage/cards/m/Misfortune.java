package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Misfortune extends CardImpl {

    public Misfortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{R}{G}");

        // An opponent chooses one - You put a +1/+1 counter on each creature you control and gain 4 life; or you put a -1/-1 counter on each creature that player controls and Misfortune deals 4 damage to that player.
        this.getSpellAbility().addEffect(new MisfortuneEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true));
    }

    private Misfortune(final Misfortune card) {
        super(card);
    }

    @Override
    public Misfortune copy() {
        return new Misfortune(this);
    }
}

class MisfortuneEffect extends OneShotEffect {

    public MisfortuneEffect() {
        super(Outcome.Neutral);
        staticText = "An opponent chooses one - "
                + "You put a +1/+1 counter on each creature you control and gain "
                + "4 life; or you put a -1/-1 counter on each creature that player "
                + "controls and Misfortune deals 4 damage to that player";
    }

    public MisfortuneEffect(final MisfortuneEffect effect) {
        super(effect);
    }

    @Override
    public MisfortuneEffect copy() {
        return new MisfortuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player chosenOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null
                && chosenOpponent != null) {
            if (chosenOpponent.chooseUse(Outcome.Neutral, "If you choose Yes, the controller puts a +1/+1 counter"
                    + "on each creature they control and they gain 4 life. If no, the controller puts a -1/-1 counter"
                    + "on each creature you control and {this} deals 4 damage to you.", source, game)) {
                Effect putP1P1CounterOnEachControlledCreature = new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent());
                putP1P1CounterOnEachControlledCreature.apply(game, source);
                controller.gainLife(4, game, source);
            } else {
                FilterCreaturePermanent filterOpponentCreatures = new FilterCreaturePermanent();
                filterOpponentCreatures.add(new ControllerIdPredicate(chosenOpponent.getId()));
                Effect putM1M1CounterOnEachOpponentCreature = new AddCountersAllEffect(
                        CounterType.M1M1.createInstance(), filterOpponentCreatures);
                putM1M1CounterOnEachOpponentCreature.apply(game, source);
                chosenOpponent.damage(4, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
