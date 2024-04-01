package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.SaddledSourceThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.SaddledMountWatcher;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class RamblingPossum extends CardImpl {

    public RamblingPossum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.POSSUM);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Rambling Possum attacks while saddled, it gains +1/+2 until end of turn. Then you may return any number of creatures that saddled it this turn to their owner's hand.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(new BoostSourceEffect(
                1, 2, Duration.EndOfTurn, "it"
        ));
        ability.addEffect(new RamblingPossumEffect());
        this.addAbility(ability, new SaddledMountWatcher());

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private RamblingPossum(final RamblingPossum card) {
        super(card);
    }

    @Override
    public RamblingPossum copy() {
        return new RamblingPossum(this);
    }
}

class RamblingPossumEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures that saddled it this turn");

    static {
        filter.add(SaddledSourceThisTurnPredicate.instance);
    }

    RamblingPossumEffect() {
        super(Outcome.Benefit);
        staticText = "then you may return any number of creatures that saddled it this turn to their owner's hand";
    }

    private RamblingPossumEffect(final RamblingPossumEffect effect) {
        super(effect);
    }

    @Override
    public RamblingPossumEffect copy() {
        return new RamblingPossumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        Set<Permanent> permanents = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return player.moveCards(permanents, Zone.HAND, source, game);
    }
}
