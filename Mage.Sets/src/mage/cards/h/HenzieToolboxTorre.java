package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class HenzieToolboxTorre extends CardImpl {

    public HenzieToolboxTorre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}");

        addSuperType(SuperType.LEGENDARY);
        addSubType(SubType.DEVIL, SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each creature spell you cast with mana value 4 or greater has blitz.
        // The blitz cost is equal to its mana cost.
        //      (You may choose to cast that spell for its blitz cost.
        //       If you do, it gains haste and “When this creature dies, draw a card.”
        //       Sacrifice it at the beginning of the next end step.)
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

    private static final FilterCreatureSpell filter = new FilterCreatureSpell();
    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    HenzieToolboxTorreGainBlitzEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each creature spell you cast with mana value 4 or greater has blitz. " +
                "The blitz cost is equal to its mana cost. " +
                "<i>(You may choose to cast that spell for its blitz cost. " +
                "If you do, it gains haste and “When this creature dies, draw a card.” " +
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

        boolean applied = false;

        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject.isCopy()
                    || !stackObject.isControlledBy(source.getControllerId())
                    || !filter.match(stackObject, controller.getId(), source, game))) {
                continue;
            }
            Card card = ((Spell) stackObject).getCard();
            game.getState().addOtherAbility(card, new BlitzAbility(card, card.getMana().toString()));
        }

        return applied;
    }

    @Override
    public HenzieToolboxTorreGainBlitzEffect copy() {
        return new HenzieToolboxTorreGainBlitzEffect(this);
    }
}

class HenzieToolboxTorreBlitzDiscountEffect extends CostModificationEffectImpl {

    HenzieToolboxTorreBlitzDiscountEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "blitz costs you pay cost {1} less for each time you’ve cast your commander from the command zone this game";
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