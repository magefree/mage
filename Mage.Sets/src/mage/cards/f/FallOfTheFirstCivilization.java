package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class FallOfTheFirstCivilization extends CardImpl {

    public FallOfTheFirstCivilization(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- You and target opponent each draw two cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new Effects(
                        new DrawCardSourceControllerEffect(2).setText("you"),
                        new DrawCardTargetEffect(2).setText("and target opponent each draw two cards")
                ), new TargetOpponent()
        );

        // II -- Exile target artifact an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, new ExileTargetEffect(),
                new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT)
        );

        // III -- Each player chooses three nonland permanents they control. Destroy all other nonland permanents.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new FallOfTheFirstCivilizationEffect());
        this.addAbility(sagaAbility);
    }

    private FallOfTheFirstCivilization(final FallOfTheFirstCivilization card) {
        super(card);
    }

    @Override
    public FallOfTheFirstCivilization copy() {
        return new FallOfTheFirstCivilization(this);
    }
}

class FallOfTheFirstCivilizationEffect extends OneShotEffect {

    FallOfTheFirstCivilizationEffect() {
        super(Outcome.Benefit);
        staticText = "each player chooses three nonland permanents they control. Destroy all other nonland permanents";
    }

    private FallOfTheFirstCivilizationEffect(final FallOfTheFirstCivilizationEffect effect) {
        super(effect);
    }

    @Override
    public FallOfTheFirstCivilizationEffect copy() {
        return new FallOfTheFirstCivilizationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int count = game
                    .getBattlefield()
                    .count(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND, playerId, source, game);
            Set<Permanent> toSave = new HashSet<>();
            switch (count) {
                case 0:
                    break;
                case 1:
                case 2:
                case 3:
                    toSave.addAll(game
                            .getBattlefield()
                            .getActivePermanents(
                                    StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND,
                                    playerId, source, game
                            ));
                    break;
                default:
                    TargetPermanent target = new TargetPermanent(3, StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
                    target.withNotTarget(true);
                    target.withChooseHint("to prevent being destroyed");
                    player.choose(outcome, target, source, game);
                    target.getTargets()
                            .stream()
                            .map(game::getPermanent)
                            .filter(Objects::nonNull)
                            .forEach(toSave::add);
            }
            if (toSave.isEmpty()) {
                game.informPlayers(player.getLogName() + " chooses no permanents");
                continue;
            }
            game.informPlayers(player.getLogName() + " chooses " +
                    CardUtil.concatWithAnd(toSave.stream().map(MageObject::getLogName).collect(Collectors.toList())));
            permanents.addAll(toSave);
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_NON_LAND,
                source.getControllerId(), source, game
        )) {
            if (!permanents.contains(permanent)) {
                permanent.destroy(source, game);
            }
        }
        return true;
    }
}
