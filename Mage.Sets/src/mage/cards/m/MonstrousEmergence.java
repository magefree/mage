package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author oscscull
 */
public final class MonstrousEmergence extends CardImpl {

    public MonstrousEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");
        
        // As an additional cost to cast this spell, choose a creature you control or reveal a creature card from your hand.
        this.getSpellAbility().addCost(new MonstrousEmergenceCost());

        // Monstrous Emergence deals damage equal to the power of the creature you chose or the card you revealed to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new MonstrousEmergenceEffect());
    }

    private MonstrousEmergence(final MonstrousEmergence card) {
        super(card);
    }

    @Override
    public MonstrousEmergence copy() {
        return new MonstrousEmergence(this);
    }
}

class MonstrousEmergenceCost extends CostImpl {

    public enum CreatureZone {
        HAND,
        BATTLEFIELD
    }

    private CreatureZone creatureZone = null;
    private UUID selectedCardId = null;

    private static final FilterCard handFilter = new FilterCard("creature card from your hand");
    private static final FilterControlledCreaturePermanent battlefieldFilter = new FilterControlledCreaturePermanent("creature you control");

    static {
        handFilter.add(CardType.CREATURE.getPredicate());
    }

    public MonstrousEmergenceCost() {
        this.text = "choose a creature you control or reveal a creature card from your hand";
    }

    private MonstrousEmergenceCost(final MonstrousEmergenceCost cost) {
        super(cost);
        this.creatureZone = cost.creatureZone;
        this.selectedCardId = cost.selectedCardId;
    }

    @Override
    public MonstrousEmergenceCost copy() {
        return new MonstrousEmergenceCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        
        // Check if player has creatures in hand
        for (UUID cardId : controller.getHand()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isCreature(game)) {
                return true;
            }
        }
        
        // Check if player has creatures on battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controllerId)) {
            if (permanent != null && permanent.isCreature(game)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        creatureZone = null;
        selectedCardId = null;
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            boolean creatureInHand = false;
            boolean creatureOnBattlefield = false;
            CreatureZone chosenZone = null;
            
            // Check for creatures in hand
            for (UUID cardId : controller.getHand()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature(game)) {
                    creatureInHand = true;
                    break;
                }
            }
            
            // Check for creatures on battlefield
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controllerId)) {
                if (permanent != null && permanent.isCreature(game)) {
                    creatureOnBattlefield = true;
                    break;
                }
            }
            
            // Player chooses between hand and battlefield if both are available
            if (creatureInHand && creatureOnBattlefield) {
                if (controller.chooseUse(Outcome.Benefit, "Choose a creature you control or reveal a creature card from your hand", 
                        null, "Choose creature you control", "Reveal creature from hand", source, game)) {
                    chosenZone = CreatureZone.BATTLEFIELD;
                } else {
                    chosenZone = CreatureZone.HAND;
                }
            }
            else if (creatureInHand) {
                chosenZone = CreatureZone.HAND;
            }
            else if (creatureOnBattlefield) {
                chosenZone = CreatureZone.BATTLEFIELD;
            }
            
            if (chosenZone != null) {
                switch (chosenZone) {
                case HAND:
                    TargetCardInHand handTarget = new TargetCardInHand(handFilter);
                    handTarget.withNotTarget(true);
                    if (controller.choose(Outcome.Benefit, handTarget, source, game)) {
                        Card card = game.getCard(handTarget.getFirstTarget());
                        if (card != null) {
                            creatureZone = CreatureZone.HAND;
                            selectedCardId = handTarget.getFirstTarget();
                            controller.revealCards(source, new CardsImpl(card), game);
                            paid = true;
                        }
                    }
                    break;
                case BATTLEFIELD:
                    TargetPermanent battlefieldTarget = new TargetPermanent(battlefieldFilter);
                    battlefieldTarget.withNotTarget(true);
                    if (controller.choose(Outcome.Benefit, battlefieldTarget, source, game)) {
                        Permanent permanent = game.getPermanent(battlefieldTarget.getFirstTarget());
                        if (permanent != null) {
                            creatureZone = CreatureZone.BATTLEFIELD;
                            selectedCardId = battlefieldTarget.getFirstTarget();
                            game.informPlayers(controller.getLogName() + " chooses " + permanent.getLogName());
                            paid = true;
                        }
                    }
                    break;
                }
            }
        }
        return paid;
    }

    public CreatureZone getCreatureZone() {
        return creatureZone;
    }

    public UUID getSelectedCardId() {
        return selectedCardId;
    }
}

class MonstrousEmergenceEffect extends OneShotEffect {

    MonstrousEmergenceEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage equal to the power of the creature you chose or the card you revealed to target creature";
    }

    private MonstrousEmergenceEffect(final MonstrousEmergenceEffect effect) {
        super(effect);
    }

    @Override
    public MonstrousEmergenceEffect copy() {
        return new MonstrousEmergenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }
        
        MonstrousEmergenceCost.CreatureZone creatureZone = null;
        UUID selectedCardId = null;
        int damage = 0;
        
        // Find the cost to get the selected creature info
        for (Cost cost : source.getCosts()) {
            if (cost instanceof MonstrousEmergenceCost) {
                MonstrousEmergenceCost monstrousEmergenceCost = (MonstrousEmergenceCost) cost;
                creatureZone = monstrousEmergenceCost.getCreatureZone();
                selectedCardId = monstrousEmergenceCost.getSelectedCardId();
                break;
            }
        }
        
        // Get the power of the selected creature
        if (creatureZone != null) {
            switch (creatureZone) {
                case HAND:
                    Card card = game.getCard(selectedCardId);
                    if (card != null) {
                        damage = card.getPower().getValue();
                    }
                    break;
                case BATTLEFIELD:
                    Permanent creature = game.getPermanentOrLKIBattlefield(selectedCardId);
                    if (creature != null) {
                        damage = creature.getPower().getValue();
                    }
                    break;
            }
        }
        
        targetCreature.damage(damage, source.getSourceId(), source, game);
        return true;
    }
}