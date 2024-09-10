package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OneRingToRuleThemAll extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonlegendary creatures");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public OneRingToRuleThemAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- The Ring tempts you, then each player mills cards equal to your Ring-bearer's power.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new TheRingTemptsYouEffect(),
                new MillCardsEachPlayerEffect(
                        OneRingToRuleThemAllValue.instance, TargetController.EACH_PLAYER
                ).setText(", then each player mills cards equal to your Ring-bearer's power")
        );

        // II -- Destroy all nonlegendary creatures.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new DestroyAllEffect(filter));

        // III -- Each opponent loses 1 life for each creature card in that player's graveyard.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new OneRingToRuleThemAllEffect());

        this.addAbility(sagaAbility);
    }

    private OneRingToRuleThemAll(final OneRingToRuleThemAll card) {
        super(card);
    }

    @Override
    public OneRingToRuleThemAll copy() {
        return new OneRingToRuleThemAll(this);
    }
}

enum OneRingToRuleThemAllValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(game.getPlayer(sourceAbility.getControllerId()))
                .filter(Objects::nonNull)
                .map(player -> player.getRingBearer(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public OneRingToRuleThemAllValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class OneRingToRuleThemAllEffect extends OneShotEffect {

    OneRingToRuleThemAllEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses 1 life for each creature card in that player's graveyard";
    }

    private OneRingToRuleThemAllEffect(final OneRingToRuleThemAllEffect effect) {
        super(effect);
    }

    @Override
    public OneRingToRuleThemAllEffect copy() {
        return new OneRingToRuleThemAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.loseLife(
                        player.getGraveyard()
                                .count(StaticFilters.FILTER_CARD_CREATURE, game),
                        game, source, false
                );
            }
        }
        return true;
    }
}
