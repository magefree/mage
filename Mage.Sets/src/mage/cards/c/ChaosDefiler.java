package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaosDefiler extends CardImpl {

    public ChaosDefiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{R}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Battle Cannon -- When Chaos Defiler enters the battlefield or dies, for each opponent, choose a nonland permanent that players controls. Destroy one of them chosen at random.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new ChaosDefilerEffect(), false
        ).withFlavorWord("Battle Cannon"));
    }

    private ChaosDefiler(final ChaosDefiler card) {
        super(card);
    }

    @Override
    public ChaosDefiler copy() {
        return new ChaosDefiler(this);
    }
}

class ChaosDefilerEffect extends OneShotEffect {

    ChaosDefilerEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, choose a nonland permanent " +
                "that players controls. Destroy one of them chosen at random";
    }

    private ChaosDefilerEffect(final ChaosDefilerEffect effect) {
        super(effect);
    }

    @Override
    public ChaosDefilerEffect copy() {
        return new ChaosDefilerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Permanent> permanents = new HashSet<>();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (!game.getBattlefield().contains(
                    StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND,
                    opponentId, source, game, 1
            )) {
                continue;
            }
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterNonlandPermanent(
                    "nonland permanent controlled by " + opponent.getName()
            );
            filter.add(new ControllerIdPredicate(opponentId));
            TargetPermanent target = new TargetPermanent(1, 1, filter, true);
            player.choose(outcome, target, source, game);
            permanents.add(game.getPermanent(target.getFirstTarget()));
        }
        permanents.removeIf(Objects::isNull);
        Permanent permanent = RandomUtil.randomFromCollection(permanents);
        return permanent != null && permanent.destroy(source, game);
    }
}
