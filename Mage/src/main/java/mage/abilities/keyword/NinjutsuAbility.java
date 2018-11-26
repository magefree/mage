package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * 702.47. Ninjutsu
 *
 * 702.47a Ninjutsu is an activated ability that functions only while the card
 * with ninjutsu is in a player's hand. "Ninjutsu [cost]" means "[Cost], Reveal
 * this card from your hand, Return an unblocked attacking creature you control
 * to its owner's hand: Put this card onto the battlefield from your hand tapped
 * and attacking."
 *
 * 702.47b The card with ninjutsu remains revealed from the time the ability is
 * announced until the ability leaves the stack.
 *
 * 702.47c A ninjutsu ability may be activated only while a creature on the
 * battlefield is unblocked (see rule 509.1h). The creature with ninjutsu is put
 * onto the battlefield unblocked. It will be attacking the same player or
 * planeswalker as the creature that was returned to its owner's hand.
 *
 *
 * @author LevelX2
 */
public class NinjutsuAbility extends ActivatedAbilityImpl {

    private final boolean commander;
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("unblocked attacker you control");

    static {
        filter.add(new UnblockedPredicate());
    }

    /**
     *
     * @param manaCost ninjutsu mana cost
     */
    public NinjutsuAbility(ManaCost manaCost) {
        this(manaCost, false);
    }

    public NinjutsuAbility(ManaCost manaCost, boolean commander) {
        super(commander ? Zone.ALL : Zone.HAND, new NinjutsuEffect(), manaCost);
        this.addCost(new RevealNinjutsuCardCost(commander));
        this.addCost(new ReturnAttackerToHandTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.commander = commander;
    }

    public NinjutsuAbility(NinjutsuAbility ability) {
        super(ability);
        this.commander = ability.commander;
    }

    @Override
    public NinjutsuAbility copy() {
        return new NinjutsuAbility(this);
    }

    @Override
    public String getRule() {
        return (commander ? "Commander n" : "N") + "injutsu "
                + getManaCostsToPay().getText() + " <i>("
                + getManaCostsToPay().getText()
                + " Return an unblocked attacker you control to hand: "
                + "Put this card onto the battlefield from your hand"
                + (commander ? " or the command zone " : " ")
                + "tapped and attacking.)</i>";
    }
}

class NinjutsuEffect extends OneShotEffect {

    public NinjutsuEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put this card onto the battlefield "
                + "from your hand tapped and attacking";
    }

    public NinjutsuEffect(final NinjutsuEffect effect) {
        super(effect);
    }

    @Override
    public NinjutsuEffect copy() {
        return new NinjutsuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                UUID defendingPlayerId = null;
                for (Cost cost : source.getCosts()) {
                    if (cost instanceof ReturnAttackerToHandTargetCost) {
                        defendingPlayerId = ((ReturnAttackerToHandTargetCost) cost).getDefendingPlayerId();
                    }
                }
                if (defendingPlayerId != null) {
                    game.getCombat().addAttackerToCombat(permanent.getId(), defendingPlayerId, game);
                    return true;
                }
            }
        }
        return false;
    }
}

class ReturnAttackerToHandTargetCost extends CostImpl {

    private UUID defendingPlayerId;

    public ReturnAttackerToHandTargetCost(TargetControlledPermanent target) {
        this.addTarget(target);
        this.defendingPlayerId = null;
        this.text = "Return an unblocked attacker you control to hand";
    }

    public ReturnAttackerToHandTargetCost(ReturnAttackerToHandTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.ReturnToHand, controllerId, sourceId, game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                defendingPlayerId = game.getCombat().getDefenderId(permanent.getId());
                paid |= permanent.moveToZone(Zone.HAND, sourceId, game, false);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public ReturnAttackerToHandTargetCost copy() {
        return new ReturnAttackerToHandTargetCost(this);
    }

    public UUID getDefendingPlayerId() {
        return defendingPlayerId;
    }
}

class RevealNinjutsuCardCost extends CostImpl {

    private final boolean commander;

    public RevealNinjutsuCardCost(boolean commander) {
        this.text = "reveal ninjutsu card";
        this.commander = commander;
    }

    public RevealNinjutsuCardCost(RevealNinjutsuCardCost cost) {
        super(cost);
        this.commander = cost.commander;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);

        Card card = player.getHand().get(ability.getSourceId(), game);
        if (card == null && commander
                && player.getCommandersIds().contains(ability.getSourceId())) {
            for (CommandObject coj : game.getState().getCommand()) {
                if (coj != null && coj.getId().equals(ability.getSourceId())) {
                    card = game.getCard(ability.getSourceId());
                    break;
                }
            }
        }
        if (card != null) {
            Cards cards = new CardsImpl(card);
            player.revealCards("Ninjutsu", cards, game);
            paid = true;
            return paid;
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public RevealNinjutsuCardCost copy() {
        return new RevealNinjutsuCardCost(this);
    }
}
