package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PersistentConstrictor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature they control");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public PersistentConstrictor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // At the beginning of each opponent's upkeep, they lose 1 life and you put a -1/-1 counter on up to one target creature they control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new PersistentConstrictorEffect(), TargetController.OPPONENT, false
        );
        ability.addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance()).concatBy("and you"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Persist
        this.addAbility(new PersistAbility());
    }

    private PersistentConstrictor(final PersistentConstrictor card) {
        super(card);
    }

    @Override
    public PersistentConstrictor copy() {
        return new PersistentConstrictor(this);
    }
}

class PersistentConstrictorEffect extends OneShotEffect {

    PersistentConstrictorEffect() {
        super(Outcome.Benefit);
        staticText = "they lose 1 life";
    }

    private PersistentConstrictorEffect(final PersistentConstrictorEffect effect) {
        super(effect);
    }

    @Override
    public PersistentConstrictorEffect copy() {
        return new PersistentConstrictorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        return player != null && player.loseLife(1, game, source, false) > 0;
    }
}
