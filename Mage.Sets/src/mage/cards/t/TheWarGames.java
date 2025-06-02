package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.WarriorToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheWarGames extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.WARRIOR, "Warrior creature");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.WARRIOR, "Warriors");
    private static final FilterControlledPermanent filter3 = new FilterControlledPermanent("nontoken creature you control");

    static {
        filter3.add(TokenPredicate.FALSE);
    }

    public TheWarGames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Each player creates three tapped 1/1 white Warrior creature tokens. The tokens are goaded for as long as The War Games remains on the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new TheWarGamesEffect());

        // II, III -- Put a +1/+1 counter on each Warrior creature.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        );

        // IV -- You may exile a nontoken creature you control. When you do, exile all Warriors.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new DoWhenCostPaid(
                        new ReflexiveTriggeredAbility(new ExileAllEffect(filter2), false),
                        new ExileTargetCost(new TargetControlledPermanent(filter3)),
                        "Exile a nontoken creature you control?"
                )
        );
        this.addAbility(sagaAbility);
    }

    private TheWarGames(final TheWarGames card) {
        super(card);
    }

    @Override
    public TheWarGames copy() {
        return new TheWarGames(this);
    }
}

class TheWarGamesEffect extends OneShotEffect {

    TheWarGamesEffect() {
        super(Outcome.Benefit);
        staticText = "each player creates three tapped 1/1 white Warrior creature tokens. " +
                "The tokens are goaded for as long as {this} remains on the battlefield";
    }

    private TheWarGamesEffect(final TheWarGamesEffect effect) {
        super(effect);
    }

    @Override
    public TheWarGamesEffect copy() {
        return new TheWarGamesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> addedTokens = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Token token = new WarriorToken();
            token.putOntoBattlefield(3, game, source, playerId, true, false);
            addedTokens.addAll(token.getLastAddedTokenIds());
        }
        if (addedTokens.isEmpty()) {
            return false;
        }
        if (source.getSourceObjectIfItStillExists(game) != null) {
            game.addEffect(
                    new GoadTargetEffect(Duration.UntilSourceLeavesBattlefield)
                            .setTargetPointer(new FixedTargets(
                                    addedTokens.stream()
                                            .map(game::getPermanent)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toSet()), game
                            )), source
            );
        }
        return true;
    }
}
