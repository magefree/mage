
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class AetherbornMarauder extends CardImpl {

    public AetherbornMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // When Aetherborn Marauder enters the battlefield, move any number of +1/+1 counters from other permanents you control onto Aetherborn Marauder.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AetherbornMarauderEffect(), false));

    }

    private AetherbornMarauder(final AetherbornMarauder card) {
        super(card);
    }

    @Override
    public AetherbornMarauder copy() {
        return new AetherbornMarauder(this);
    }
}

class AetherbornMarauderEffect extends OneShotEffect {

    public AetherbornMarauderEffect() {
        super(Outcome.Benefit);
        this.staticText = "move any number of +1/+1 counters from other permanents you control onto {this}";
    }

    public AetherbornMarauderEffect(final AetherbornMarauderEffect effect) {
        super(effect);
    }

    @Override
    public AetherbornMarauderEffect copy() {
        return new AetherbornMarauderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (controller != null && sourceObject != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent("permanent you control to remove +1/+1 counters from");
            filter.add(AnotherPredicate.instance);
            filter.add(CounterType.P1P1.getPredicate());
            boolean firstRun = true;
            while (game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0) {
                if (controller.chooseUse(outcome, "Move " + (firstRun ? "any" : "more") + " +1/+1 counters from other permanents you control to " + sourceObject.getLogName() + '?', source, game)) {
                    firstRun = false;
                    TargetControlledPermanent target = new TargetControlledPermanent(filter);
                    target.setNotTarget(true);
                    if (target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), source, game)) {
                        Permanent fromPermanent = game.getPermanent(target.getFirstTarget());
                        if (fromPermanent != null) {
                            int numberOfCounters = fromPermanent.getCounters(game).getCount(CounterType.P1P1);
                            int numberToMove = 1;
                            if (numberOfCounters > 1) {
                                numberToMove = controller.getAmount(0, numberOfCounters, "Choose how many +1/+1 counters to move", game);
                            }
                            if (numberToMove > 0) {
                                fromPermanent.removeCounters(CounterType.P1P1.createInstance(numberToMove), source, game);
                                sourceObject.addCounters(CounterType.P1P1.createInstance(numberToMove), source.getControllerId(), source, game);
                            }
                        }
                    }
                } else {
                    break;
                }

            }
            return true;
        }
        return false;
    }
}
