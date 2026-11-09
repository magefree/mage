package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HenzieToolboxTorre extends CardImpl {

    public HenzieToolboxTorre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each creature you cast with mana value 4 or greater has blitz. The blitz cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(new HenzieToolboxTorreGainBlitzEffect()));

        // Blitz costs you pay cost {1} less for each time you’ve cast your commander from the command zone this game.
        this.addAbility(new SimpleStaticAbility(new HenzieToolboxTorreBlitzDiscountEffect()));

    }

    private HenzieToolboxTorre(final HenzieToolboxTorre card) {
        super(card);
    }

    @Override
    public HenzieToolboxTorre copy() {
        return new HenzieToolboxTorre(this);
    }
}

class HenzieToolboxTorreGainBlitzEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filterCard = new FilterCreatureCard("creature spell you cast with mana value 4 or greater");
    private static final FilterCreatureSpell filterSpell = new FilterCreatureSpell("creature spell you cast with mana value 4 or greater");

    static {
        // a card with {X} can be cast with mana value 4 or greater (X is 0 only while the card is not on the stack)
        filterCard.add(Predicates.or(
                new ManaValuePredicate(ComparisonType.OR_GREATER, 4),
                VariableManaCostPredicate.instance
        ));
        filterSpell.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
    }

    HenzieToolboxTorreGainBlitzEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each creature spell you cast with mana value 4 or greater has blitz. " +
                "The blitz cost is equal to its mana cost. " +
                "<i>(You may choose to cast that spell for its blitz cost. " +
                "If you do, it gains haste and \"When this creature dies, draw a card.\" " +
                "Sacrifice it at the beginning of the next end step.)</i>";
    }

    private HenzieToolboxTorreGainBlitzEffect(final HenzieToolboxTorreGainBlitzEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = new LinkedHashSet<>();
        cards.addAll(controller.getHand().getCards(game));
        cards.addAll(controller.getGraveyard().getCards(game));
        cards.addAll(controller.getLibrary().getCards(game));
        cards.addAll(game.getExile().getCardsInRange(game, controller.getId()));
        cards.addAll(game.getCommanderCardsFromCommandZone(controller, CommanderCardType.ANY));
        Set<Card> cardsToGainBlitz = new LinkedHashSet<>();
        for (Card card : cards) {
            // double-faced cards are cast by their faces, so the castable faces must have the blitz ability
            cardsToGainBlitz.addAll(CardUtil.getCastableComponents(card, filterCard, source, controller, game, null, false));
        }
        game.getStack().stream()
                .filter(s -> s.isControlledBy(controller.getId()))
                .filter(s -> filterSpell.match(s, controller.getId(), source, game))
                .map(s -> game.getCard(s.getSourceId()))
                .filter(Objects::nonNull)
                .forEach(cardsToGainBlitz::add);
        for (Card card : cardsToGainBlitz) {
            if (card.getManaCost().getText().isEmpty()) {
                continue; // card must have a mana cost
            }
            Ability ability = new BlitzAbility(card, card.getManaCost().getText());
            // 202.3e ... X is treated as the number chosen for it while the object is on the stack.
            // so a spell with {X} has blitz only with X big enough for mana value 4 or greater
            VariableManaCost xCost = card.getManaCost().stream()
                    .filter(VariableManaCost.class::isInstance)
                    .map(VariableManaCost.class::cast)
                    .findFirst()
                    .orElse(null);
            int needManaValue = 4 - card.getManaValue();
            if (xCost != null && needManaValue > 0) {
                int minX = (needManaValue + xCost.getXInstancesCount() - 1) / xCost.getXInstancesCount();
                ability.setVariableCostsMinMax(minX, Integer.MAX_VALUE);
            }
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getControllerOrOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public HenzieToolboxTorreGainBlitzEffect copy() {
        return new HenzieToolboxTorreGainBlitzEffect(this);
    }
}

class HenzieToolboxTorreBlitzDiscountEffect extends CostModificationEffectImpl {

    HenzieToolboxTorreBlitzDiscountEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "blitz costs you pay cost {1} less for each time you've cast your commander from the command zone this game";
    }

    private HenzieToolboxTorreBlitzDiscountEffect(final HenzieToolboxTorreBlitzDiscountEffect effect) {
        super(effect);
    }

    @Override
    public HenzieToolboxTorreBlitzDiscountEffect copy() {
        return new HenzieToolboxTorreBlitzDiscountEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        if (controller == null || watcher == null) {
            return false;
        }

        int playCount = game
                .getCommandersIds(controller, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)
                .stream()
                .mapToInt(watcher::getPlaysCount)
                .sum();
        if (playCount == 0) {
            return false;
        }
        CardUtil.reduceCost(abilityToModify, playCount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        return abilityToModify instanceof BlitzAbility;
    }
}
