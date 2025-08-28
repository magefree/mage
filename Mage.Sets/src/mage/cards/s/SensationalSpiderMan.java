package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetPermanentAmount;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SensationalSpiderMan extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public SensationalSpiderMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sensational Spider-Man attacks, tap target creature defending player controls and put a stun counter on it. Then you may remove up to three stun counters from among all permanents. Draw cards equal to the number of stun counters removed this way.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addEffect(new SensationalSpiderManEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SensationalSpiderMan(final SensationalSpiderMan card) {
        super(card);
    }

    @Override
    public SensationalSpiderMan copy() {
        return new SensationalSpiderMan(this);
    }
}

class SensationalSpiderManEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("permanents with stun counters on them");

    static {
        filter.add(CounterType.STUN.getPredicate());
    }

    SensationalSpiderManEffect() {
        super(Outcome.Benefit);
        staticText = "then you may remove up to three stun counters from among all permanents. " +
                "Draw cards equal to the number of stun counters removed this way";
    }

    private SensationalSpiderManEffect(final SensationalSpiderManEffect effect) {
        super(effect);
    }

    @Override
    public SensationalSpiderManEffect copy() {
        return new SensationalSpiderManEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        // TODO: this ideally would be able to prevent the player from choosing a number greater than the number of stun counters available to remove
        TargetPermanentAmount target = new TargetPermanentAmount(3, 0, filter);
        target.withNotTarget(true);
        player.chooseTarget(outcome, target, source, game);
        int amountRemoved = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .mapToInt(permanent -> permanent.removeCounters(
                        CounterType.STUN.createInstance(target.getTargetAmount(permanent.getId())), source, game
                ))
                .sum();
        if (amountRemoved > 1) {
            player.drawCards(amountRemoved, source, game);
            return true;
        }
        return false;
    }
}
