package mage.cards.z;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CanBeSacrificedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZodiarkUmbralGod extends CardImpl {

    public ZodiarkUmbralGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When Zodiark enters, each player sacrifices half the non-God creatures they control of their choice, rounded down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ZodiarkUmbralGodEffect()));

        // Whenever a player sacrifices another creature, put a +1/+1 counter on Zodiark.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_ANOTHER_CREATURE, TargetController.ANY
        ));
    }

    private ZodiarkUmbralGod(final ZodiarkUmbralGod card) {
        super(card);
    }

    @Override
    public ZodiarkUmbralGod copy() {
        return new ZodiarkUmbralGod(this);
    }
}

class ZodiarkUmbralGodEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("non-God creatures you control");
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent("non-God creatures you control");
    private static final Predicate<MageObject> predicate = Predicates.not(SubType.GOD.getPredicate());

    static {
        filter.add(predicate);
        filter2.add(predicate);
        filter2.add(CanBeSacrificedPredicate.instance);
    }

    ZodiarkUmbralGodEffect() {
        super(Outcome.Benefit);
        staticText = "each player sacrifices half the non-God creatures they control of their choice, rounded down";
    }

    private ZodiarkUmbralGodEffect(final ZodiarkUmbralGodEffect effect) {
        super(effect);
    }

    @Override
    public ZodiarkUmbralGodEffect copy() {
        return new ZodiarkUmbralGodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            // need to take into account how many creatures can actually be sacrificed
            int count = Math.min(
                    game.getBattlefield().count(filter, playerId, source, game) / 2,
                    game.getBattlefield().count(filter2, playerId, source, game)
            );
            if (count < 1) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(count, count, filter2, true);
            target.withChooseHint("to sacrifice");
            player.choose(outcome, target, source, game);
            target.getTargets()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .forEach(permanents::add);
        }
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source, game);
        }
        return true;
    }
}
