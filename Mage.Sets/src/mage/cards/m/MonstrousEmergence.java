package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonstrousEmergence extends CardImpl {

    public MonstrousEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // As an additional cost to cast this spell, choose a creature you control or reveal a creature card from your hand.
        this.getSpellAbility().addCost(new MonstrousEmergenceCost());

        // Monstrous Emergence deals damage equal to the power of the creature you chose or the card you revealed to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(MonstrousEmergenceValue.instance)
                .setText("{this} deals damage equal to the power of the creature you chose or the card you revealed to target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MonstrousEmergence(final MonstrousEmergence card) {
        super(card);
    }

    @Override
    public MonstrousEmergence copy() {
        return new MonstrousEmergence(this);
    }
}

enum MonstrousEmergenceValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Card) effect.getValue("monstrousEmergenceCost"))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public MonstrousEmergenceValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "power of the creature you chose or the card you revealed";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class MonstrousEmergenceCost extends CostImpl {

    public MonstrousEmergenceCost() {
        super();
        this.text = "choose a creature you control or reveal a creature card from your hand";
    }

    private MonstrousEmergenceCost(final MonstrousEmergenceCost cost) {
        super(cost);
    }

    @Override
    public MonstrousEmergenceCost copy() {
        return new MonstrousEmergenceCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getHand)
                .filter(hand -> hand.count(StaticFilters.FILTER_CARD_CREATURE, game) > 0)
                .isPresent()
                || game
                .getBattlefield()
                .contains(StaticFilters.FILTER_CONTROLLED_CREATURE, controllerId, source, game, 1);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            paid = false;
            return paid;
        }
        boolean hasPermanent = game
                .getBattlefield()
                .contains(StaticFilters.FILTER_CONTROLLED_CREATURE, controllerId, source, game, 1);
        boolean hasHand = player.getHand().count(StaticFilters.FILTER_CARD_CREATURE, game) > 0;
        boolean usePermanent;
        if (hasPermanent && hasHand) {
            usePermanent = player.chooseUse(
                    Outcome.Neutral, "Choose a creature you control or reveal a creature card from your hand?",
                    null, "Choose controlled", "Reveal from hand", source, game
            );
        } else if (hasPermanent) {
            usePermanent = true;
        } else if (hasHand) {
            usePermanent = false;
        } else {
            paid = false;
            return paid;
        }
        if (usePermanent) {
            TargetPermanent target = new TargetControlledCreaturePermanent();
            target.withNotTarget(true);
            player.choose(Outcome.Neutral, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                paid = false;
                return paid;
            }
            game.informPlayers(player.getLogName() + " chooses " + permanent.getLogName() + " on the battlefield");
            source.getEffects().setValue("monstrousEmergenceCost", permanent);
            paid = true;
            return true;
        }
        TargetCard target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE);
        player.choose(Outcome.Neutral, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            paid = false;
            return paid;
        }
        player.revealCards(source, new CardsImpl(card), game);
        game.informPlayers(player.getLogName() + " reveals " + card.getLogName() + " from their hand");
        source.getEffects().setValue("monstrousEmergenceCost", card);
        paid = true;
        return paid;
    }
}
