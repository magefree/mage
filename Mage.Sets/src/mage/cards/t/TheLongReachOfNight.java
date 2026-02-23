package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.SacrificeOpponentsUnlessPayEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.DefendingPlayerOwnsCardPredicate;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheLongReachOfNight extends TransformingDoubleFacedCard {

    private static final FilterCard filter
            = new FilterCreatureCard("creature cards in defending player's graveyard");

    static {
        filter.add(DefendingPlayerOwnsCardPredicate.instance);
    }

    private static final DynamicValue xValue = new CardsInAllGraveyardsCount(filter);

    public TheLongReachOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{3}{B}",
                "Animus of Night's Reach",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "B"
        );

        // The Long Reach of Night
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II — Each opponent sacrifices a creature unless they discard a card.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new SacrificeOpponentsUnlessPayEffect(
                        new DiscardCardCost(), StaticFilters.FILTER_PERMANENT_CREATURE
                )
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect()
        );

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Animus of Night's Reach
        this.getRightHalfCard().setPT(0, 4);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // Whenever Animus of Night's Reach attacks, it gets +X/+0 until end of turn, where X is the number of creature cards in defending player's graveyard.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn
        ).setText("it gets +X/+0 until end of turn, where X is the number of creature cards in defending player's graveyard")).addHint(AnimusOfNightsReachHint.instance));
    }

    private TheLongReachOfNight(final TheLongReachOfNight card) {
        super(card);
    }

    @Override
    public TheLongReachOfNight copy() {
        return new TheLongReachOfNight(this);
    }
}

enum AnimusOfNightsReachHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        return "Cards in each opponent's graveyard:<br>"
                + game
                .getOpponents(ability.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player
                        .getName()
                        + ": " + player
                        .getGraveyard()
                        .count(StaticFilters.FILTER_CARD_CREATURE, game))
                .collect(Collectors.joining("<br>"));
    }

    @Override
    public AnimusOfNightsReachHint copy() {
        return instance;
    }
}
