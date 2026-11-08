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
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature spell you cast with mana value 4 or greater");
    private static final FilterCreatureSpell filter2 = new FilterCreatureSpell("creature spell you cast with mana value 4 or greater");
    static {
        filter.add(Predicates.or(
                new ManaValuePredicate(ComparisonType.OR_GREATER, 4),
                // a card outside the stack treats X as 0, so any {X} creature could reach 4 or greater
                (Predicate<Card>) (input, game) -> input.getManaCost().containsX()
        ));
        // a spell on the stack already counts its chosen X, so only its actual mana value matters
        filter2.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
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
        Set<Card> cardsToGainBlitz = Stream.concat(
                        Stream.<Collection<Card>>of(
                                        controller.getHand().getCards(game),
                                        controller.getGraveyard().getCards(game),
                                        controller.getLibrary().getCards(game),
                                        game.getExile().getCardsInRange(game, controller.getId()),
                                        game.getCommanderCardsFromCommandZone(controller, CommanderCardType.ANY)
                                )
                                .flatMap(Collection::stream)
                                .flatMap(card -> CardUtil.getCastableComponents(card, filter, source, controller, game, null, false).stream()),
                        game.getStack().stream()
                                .filter(s -> s.isControlledBy(controller.getId()))
                                .filter(s -> filter2.match(s, controller.getId(), source, game))
                                .map(s -> game.getCard(s.getSourceId()))
                                .filter(Objects::nonNull)
                )
                .collect(Collectors.toSet());
        for (Card card : cardsToGainBlitz) {
            if (card.getManaCost().getText().isEmpty()) {
                continue; // card must have a mana cost
            }
            Ability ability = new BlitzAbility(card, card.getManaCost().getText());
            int xInstances = card.getManaCost().getVariableCosts().stream()
                    .filter(VariableManaCost.class::isInstance)
                    .map(VariableManaCost.class::cast)
                    .mapToInt(VariableManaCost::getXInstancesCount)
                    .sum();
            int missingManaValue = 4 - card.getManaValue();
            if (xInstances > 0 && missingManaValue > 0) {
                // the chosen X must still make the spell's mana value 4 or greater
                ability.setVariableCostsMinMax((missingManaValue + xInstances - 1) / xInstances, Integer.MAX_VALUE);
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
