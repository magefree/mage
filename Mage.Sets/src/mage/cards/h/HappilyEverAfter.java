package mage.cards.h;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.*;

/**
 * @author TheElk801
 */
public final class HappilyEverAfter extends CardImpl {

    public HappilyEverAfter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Happily Ever After enters the battlefield, each player gains 5 life and draws a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HappilyEverAfterEffect()));

        // At the beginning of your upkeep, if there are five colors among permanents you control, there are six or more card types among permanents you control and/or cards in your graveyard, and your life total is greater than or equal to your starting life total, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new WinGameSourceControllerEffect(), TargetController.YOU, false
                ), HappilyEverAfterCondition.instance, "At the beginning of your upkeep, " +
                "if there are five colors among permanents you control, there are six or more card types " +
                "among permanents you control and/or cards in your graveyard, and your life total is " +
                "greater than or equal to your starting life total, you win the game."
        ).addHint(HappilyEverAfterColorHint.instance).addHint(HappilyEverAfterCardTypeHint.instance));
    }

    private HappilyEverAfter(final HappilyEverAfter card) {
        super(card);
    }

    @Override
    public HappilyEverAfter copy() {
        return new HappilyEverAfter(this);
    }
}

class HappilyEverAfterEffect extends OneShotEffect {

    HappilyEverAfterEffect() {
        super(Outcome.GainLife);
        staticText = "each player gains 5 life and draws a card";
    }

    private HappilyEverAfterEffect(final HappilyEverAfterEffect effect) {
        super(effect);
    }

    @Override
    public HappilyEverAfterEffect copy() {
        return new HappilyEverAfterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEachOrdered(player -> {
                    player.gainLife(5, game, source);
                    player.drawCards(1, source, game);
                });
        return true;
    }
}

enum HappilyEverAfterCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getLife() < game.getStartingLife()) {
            return false;
        }
        ObjectColor color = new ObjectColor("");
        game.getBattlefield()
                .getAllActivePermanents(source.getControllerId())
                .stream()
                .map(permanent -> permanent.getColor(game))
                .forEach(color::addColor);
        if (color.getColorCount() < 5) {
            return false;
        }
        EnumSet<CardType> cardTypeEnumSet = EnumSet.noneOf(CardType.class);
        game.getBattlefield()
                .getAllActivePermanents(source.getControllerId())
                .stream()
                .map(permanent -> permanent.getCardType(game))
                .flatMap(Collection::stream)
                .forEach(cardTypeEnumSet::add);
        if (cardTypeEnumSet.size() >= 6) {
            return true;
        }
        player.getGraveyard()
                .getCards(game)
                .stream()
                .map(card -> card.getCardType(game))
                .flatMap(Collection::stream)
                .forEach(cardTypeEnumSet::add);
        return cardTypeEnumSet.size() >= 6;
    }
}

enum HappilyEverAfterColorHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return null;
        }
        ObjectColor color = new ObjectColor("");
        game.getBattlefield()
                .getAllActivePermanents(ability.getControllerId())
                .stream()
                .map(permanent -> permanent.getColor(game))
                .forEach(color::addColor);
        if (color.isColorless()) {
            return "Colors of permanents you control: None";
        }
        List<String> colors = new ArrayList<>();
        if (color.isWhite()) {
            colors.add("white");
        }
        if (color.isBlue()) {
            colors.add("blue");
        }
        if (color.isBlack()) {
            colors.add("black");
        }
        if (color.isRed()) {
            colors.add("red");
        }
        if (color.isGreen()) {
            colors.add("green");
        }
        return "Colors of permanents you control: " + color.getColorCount() + " (" + colors.stream().reduce((a, b) -> a + ", " + b) + ")";
    }

    @Override
    public HappilyEverAfterColorHint copy() {
        return instance;
    }
}

enum HappilyEverAfterCardTypeHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return null;
        }
        Set<CardType> cardTypeSet = EnumSet.noneOf(CardType.class);
        game.getBattlefield()
                .getAllActivePermanents(ability.getControllerId())
                .stream()
                .map(permanent -> permanent.getCardType(game))
                .flatMap(Collection::stream)
                .forEach(cardTypeSet::add);
        player.getGraveyard()
                .getCards(game)
                .stream()
                .map(card -> card.getCardType(game))
                .flatMap(Collection::stream)
                .forEach(cardTypeSet::add);
        if (cardTypeSet.isEmpty()) {
            return "Card types on battlefield and in graveyard: None";
        }
        String message = cardTypeSet
                .stream()
                .distinct()
                .sorted()
                .map(CardType::toString)
                .reduce((a, b) -> a + ", " + b)
                .get();
        return "Card types on battlefield and in graveyard: " + cardTypeSet.size() + " (" + message + ")";
    }

    @Override
    public HappilyEverAfterCardTypeHint copy() {
        return instance;
    }
}
