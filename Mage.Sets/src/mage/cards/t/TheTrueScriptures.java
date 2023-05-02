package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheTrueScriptures extends CardImpl {

    public TheTrueScriptures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.SAGA);
        this.color.setBlack(true);
        this.nightCard = true;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- For each opponent, destroy up to one target creature or planeswalker that player controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I, false,
                ability -> {
                    ability.addEffect(new DestroyTargetEffect().setTargetPointer(new EachTargetPointer())
                            .setText("for each opponent, destroy up to one target creature or planeswalker that player controls"));
                    ability.setTargetAdjuster(TheTrueScripturesAdjuster.instance);
                }
        );

        // II -- Each opponent discards three cards, then mills three cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new DiscardEachPlayerEffect(StaticValue.get(3), false, TargetController.OPPONENT),
                new MillCardsEachPlayerEffect(3, TargetController.OPPONENT).setText(", then mills three cards")
        );

        // III -- Put all creature cards from all graveyards onto the battlefield under your control. Exile The True Scriptures, then return it to the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new TheTrueScripturesEffect(),
                new ExileSourceAndReturnFaceUpEffect()
        );
        this.addAbility(sagaAbility);
    }

    private TheTrueScriptures(final TheTrueScriptures card) {
        super(card);
    }

    @Override
    public TheTrueScriptures copy() {
        return new TheTrueScriptures(this);
    }
}

enum TheTrueScripturesAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getOpponents(ability.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
                    "creature or planswalker controlled by " + player.getName()
            );
            filter.add(new ControllerIdPredicate(playerId));
            ability.addTarget(new TargetPermanent(0, 1, filter));
        }
    }
}

class TheTrueScripturesEffect extends OneShotEffect {

    TheTrueScripturesEffect() {
        super(Outcome.Benefit);
        staticText = "put all creature cards from all graveyards onto the battlefield under your control";
    }

    private TheTrueScripturesEffect(final TheTrueScripturesEffect effect) {
        super(effect);
    }

    @Override
    public TheTrueScripturesEffect copy() {
        return new TheTrueScripturesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(gy -> gy.getCards(StaticFilters.FILTER_CARD_CREATURE, game))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
        return player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
