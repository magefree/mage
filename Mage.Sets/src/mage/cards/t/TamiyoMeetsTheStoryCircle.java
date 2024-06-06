package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TamiyoMeetsTheStoryCircle extends CardImpl {

    public TamiyoMeetsTheStoryCircle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Until your next turn, whenever a creature attacks you or a planeswalker you control, it gets -2/-0 until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new CreateDelayedTriggeredAbilityEffect(
                        new UntilYourNextTurnDelayedTriggeredAbility(
                                new AttacksAllTriggeredAbility(
                                        new BoostTargetEffect(-2, -0),
                                        false,
                                        StaticFilters.FILTER_PERMANENT_CREATURE,
                                        SetTargetPointer.PERMANENT,
                                        true
                                )
                        )
                )
        );

        // II -- Discard any number of cards, then investigate twice for each card discarded this way.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new TamiyoMeetsTheStoryCircleMinusTwoEffect()
        );

        // III -- Shuffle up to three target cards from your graveyard into your library.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new ShuffleIntoLibraryTargetEffect(),
                new TargetCardInYourGraveyard(0, 3)
        );

        this.addAbility(sagaAbility);
    }

    private TamiyoMeetsTheStoryCircle(final TamiyoMeetsTheStoryCircle card) {
        super(card);
    }

    @Override
    public TamiyoMeetsTheStoryCircle copy() {
        return new TamiyoMeetsTheStoryCircle(this);
    }
}

class TamiyoMeetsTheStoryCircleMinusTwoEffect extends OneShotEffect {

    TamiyoMeetsTheStoryCircleMinusTwoEffect() {
        super(Outcome.Benefit);
        staticText = "Discard any number of cards, then investigate twice for each card discarded this way";
    }

    private TamiyoMeetsTheStoryCircleMinusTwoEffect(final TamiyoMeetsTheStoryCircleMinusTwoEffect effect) {
        super(effect);
    }

    @Override
    public TamiyoMeetsTheStoryCircleMinusTwoEffect copy() {
        return new TamiyoMeetsTheStoryCircleMinusTwoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int discarded = player.discard(
                0, Integer.MAX_VALUE, false, source, game
        ).size();
        InvestigateEffect.doInvestigate(player.getId(), discarded * 2, game, source);
        return true;
    }
}