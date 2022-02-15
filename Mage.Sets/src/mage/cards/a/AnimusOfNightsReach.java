package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
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
public final class AnimusOfNightsReach extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature cards in defending player's graveyard");

    static {
        filter.add(DefendingPlayerOwnsCardPredicate.instance);
    }

    private static final DynamicValue xValue = new CardsInAllGraveyardsCount(filter);

    public AnimusOfNightsReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Animus of Night's Reach attacks, it gets +X/+0 until end of turn, where X is the number of creature cards in defending player's graveyard.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn, true
        ).setText("it gets +X/+0 until end of turn, where X is the number of creature cards in defending player's graveyard")).addHint(AnimusOfNightsReachHint.instance));
    }

    private AnimusOfNightsReach(final AnimusOfNightsReach card) {
        super(card);
    }

    @Override
    public AnimusOfNightsReach copy() {
        return new AnimusOfNightsReach(this);
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