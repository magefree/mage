
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class Magmasaur extends CardImpl {

    public Magmasaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Magmasaur enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                "with five +1/+1 counters on it"));

        // At the beginning of your upkeep, you may remove a +1/+1 counter from Magmasaur. If you don't, sacrifice Magmasaur and it deals damage equal to the number of +1/+1 counters on it to each creature without flying and each player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MagmasaurEffect(), TargetController.YOU, false, false));
    }

    private Magmasaur(final Magmasaur card) {
        super(card);
    }

    @Override
    public Magmasaur copy() {
        return new Magmasaur(this);
    }
}

class MagmasaurEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public MagmasaurEffect() {
        super(Outcome.Damage);
        this.staticText = "you may remove a +1/+1 counter from {this}. If you don't, sacrifice {this} and it deals damage equal to the number of +1/+1 counters on it to each creature without flying and each player";
    }

    public MagmasaurEffect(final MagmasaurEffect effect) {
        super(effect);
    }

    @Override
    public MagmasaurEffect copy() {
        return new MagmasaurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
        if (sourceObject != null && controller != null) {
            if (controller.chooseUse(outcome, "Remove a +1/+1 counter from " + sourceObject.getLogName() + '?', source, game)) {
                sourceObject.removeCounters(CounterType.P1P1.getName(), 1, source, game);
            } else {
                int counters = sourceObject.getCounters(game).getCount(CounterType.P1P1);
                sourceObject.sacrifice(source, game);
                new DamageEverythingEffect(counters, filter).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
