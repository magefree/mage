
package mage.cards.k;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class KozilekTheGreatDistortion extends CardImpl {

    public KozilekTheGreatDistortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{C}{C}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // When you cast Kozilek, the Great Distortion, if you have fewer than seven cards in hand, draw cards equal to the difference.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new CastSourceTriggeredAbility(new KozilekDrawEffect(), false),
                new CardsInHandCondition(ComparisonType.FEWER_THAN, 7),
                "When you cast this spell, if you have fewer than seven cards in hand, draw cards equal to the difference."));
        // Menace
        this.addAbility(new MenaceAbility(false));

        // Discard a card with converted mana cost X: Counter target spell with converted mana cost X.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new KozilekDiscardCost());
        ability.addTarget(new TargetSpell(new FilterSpell("spell with mana value X")));
        this.addAbility(ability);
    }

    private KozilekTheGreatDistortion(final KozilekTheGreatDistortion card) {
        super(card);
    }

    @Override
    public KozilekTheGreatDistortion copy() {
        return new KozilekTheGreatDistortion(this);
    }
}

class KozilekDrawEffect extends OneShotEffect {

    public KozilekDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "if you have fewer than seven cards in hand, draw cards equal to the difference";
    }

    public KozilekDrawEffect(final KozilekDrawEffect effect) {
        super(effect);
    }

    @Override
    public KozilekDrawEffect copy() {
        return new KozilekDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(7 - controller.getHand().size(), source, game);
            return true;
        }
        return false;
    }
}

class KozilekDiscardCost extends CostImpl {

    public KozilekDiscardCost() {
        this.text = "discard a card with mana value X";
    }

    public KozilekDiscardCost(final KozilekDiscardCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Spell targetSpell = game.getStack().getSpell(ability.getFirstTarget());
        if (targetSpell == null) {
            return false;
        }
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card with mana value of " + targetSpell.getManaValue());
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, targetSpell.getManaValue()));
        TargetCardInHand target = new TargetCardInHand(filter);
        this.getTargets().clear();
        this.getTargets().add(target);
        if (targets.choose(Outcome.Discard, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card == null) {
                    return false;
                }
                player.discard(card, true, source, game);
                paid = true;
            }
        }
        return paid;
    }

    @Override
    public void clearPaid() {
        super.clearPaid();
        this.targets.clearChosen();
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        if (game.getStack().isEmpty()) {
            return false;
        }
        Set<Integer> stackCMC = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell) {
                stackCMC.add(stackObject.getManaValue());
            }
        }
        Player controller = game.getPlayer(ability.getControllerId());
        if(controller != null) {
            for (Card card : controller.getHand().getCards(game)) {
                if (stackCMC.contains(card.getManaValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public KozilekDiscardCost copy() {
        return new KozilekDiscardCost(this);
    }

}
