package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThePartingOfTheWays extends CardImpl {

    public ThePartingOfTheWays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Exile the top five cards of your library. For each nonland card exiled this way, put a number of time counters on that card equal to its mana value. If it doesn't have suspend, it gains suspend.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new ThePartingOfTheWaysEffect());

        // II -- Time travel, then time travel.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new TimeTravelEffect(false),
                new TimeTravelEffect(false)
                        .concatBy(", then")
        );

        // III -- For each opponent, destroy up to one target artifact that player controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                ability -> {
                    ability.addEffect(new DestroyTargetEffect()
                            .setText("For each opponent, destroy up to one target artifact that player controls"));
                    ability.addTarget(new TargetArtifactPermanent(0, 1));
                    ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
                }
        );
        this.addAbility(sagaAbility);
    }

    private ThePartingOfTheWays(final ThePartingOfTheWays card) {
        super(card);
    }

    @Override
    public ThePartingOfTheWays copy() {
        return new ThePartingOfTheWays(this);
    }
}

class ThePartingOfTheWaysEffect extends OneShotEffect {

    ThePartingOfTheWaysEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top five cards of your library. For each nonland card exiled this way, " +
                "put a number of time counters on that card equal to its mana value. " +
                "If it doesn't have suspend, it gains suspend";
    }

    private ThePartingOfTheWaysEffect(final ThePartingOfTheWaysEffect effect) {
        super(effect);
    }

    @Override
    public ThePartingOfTheWaysEffect copy() {
        return new ThePartingOfTheWaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        for (Card card : cards.getCards(StaticFilters.FILTER_CARD_NON_LAND, game)) {
            SuspendAbility.addTimeCountersAndSuspend(card, card.getManaValue(), source, game);
        }
        return true;
    }
}
