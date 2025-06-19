package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class BanditsTalent extends CardImpl {

    public BanditsTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());
        // When Bandit's Talent enters, each opponent discards two cards unless they discard a nonland card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BanditsTalentDiscardEffect()
        ));

        // {B}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{B}"));
        // At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, they lose 2 life.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new BeginningOfUpkeepTriggeredAbility(
                TargetController.OPPONENT, new ConditionalOneShotEffect(new LoseLifeTargetEffect(2).setText("they lose 2 life"), new CardsInHandCondition(ComparisonType.OR_LESS, 1, TargetController.ACTIVE)),
                false), 2)));

        // {3}{B}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{B}"));
        // At the beginning of your draw step, draw an additional card for each opponent who has one or fewer cards in hand.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new BeginningOfDrawTriggeredAbility(
                TargetController.YOU, new DrawCardSourceControllerEffect(BanditsTalentValue.instance), false), 3))
                .addHint(BanditsTalentValue.getHint()));
    }

    private BanditsTalent(final BanditsTalent card) {
        super(card);
    }

    @Override
    public BanditsTalent copy() {
        return new BanditsTalent(this);
    }
}

//Combination of Wrench Mind and DiscardEachPlayerEffect
class BanditsTalentDiscardEffect extends OneShotEffect {

    BanditsTalentDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "each opponent discards two cards unless they discard a nonland card";
    }

    private BanditsTalentDiscardEffect(final BanditsTalentDiscardEffect effect) {
        super(effect);
    }

    @Override
    public BanditsTalentDiscardEffect copy() {
        return new BanditsTalentDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        // Store for each player the cards to discard, that's important because all discard shall happen at the same time
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player targetPlayer = game.getPlayer(playerId);
            if (targetPlayer == null || targetPlayer.getHand().isEmpty() || !controller.hasOpponent(playerId, game)) {
                continue;
            }

            int numberOfCardsToDiscard = Math.min(2, targetPlayer.getHand().size());
            Cards cards = new CardsImpl();

            if (numberOfCardsToDiscard > 0) {
                if (targetPlayer.getHand().count(StaticFilters.FILTER_CARD_NON_LAND, game) < 1
                        || !targetPlayer.chooseUse(Outcome.Discard, "Discard a nonland card?", source, game)) {
                    //Discard 2 cards
                    Target target = new TargetDiscard(
                            numberOfCardsToDiscard, numberOfCardsToDiscard,
                            StaticFilters.FILTER_CARD, playerId
                    );
                    targetPlayer.chooseTarget(outcome, target, source, game);
                    cards.addAll(target.getTargets());
                } else {
                    //Discard a nonland
                    Target target = new TargetDiscard(
                            1, 1,
                            StaticFilters.FILTER_CARD_NON_LAND, playerId
                    );
                    targetPlayer.chooseTarget(outcome, target, source, game);
                    cards.addAll(target.getTargets());
                }
                cardsToDiscard.put(playerId, cards);
            }
        }

        // discard all chosen cards
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.discard(cardsToDiscard.get(playerId), false, source, game);
        }
        return true;
    }
}

enum BanditsTalentValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Opponents who have one or fewer cards in hand", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(game::getPlayer)
                .map(Player::getHand)
                .mapToInt(Set::size)
                .map(x -> x <= 1 ? 1 : 0)
                .sum();
    }

    @Override
    public BanditsTalentValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "opponent who has one or fewer cards in hand";
    }

    @Override
    public String toString() {
        return "an additional card";
    }
}
