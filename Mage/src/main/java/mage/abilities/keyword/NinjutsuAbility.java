package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * 702.47. Ninjutsu
 * <p>
 * 702.47a Ninjutsu is an activated ability that functions only while the card
 * with ninjutsu is in a player's hand. "Ninjutsu [cost]" means "[Cost], Reveal
 * this card from your hand, Return an unblocked attacking creature you control
 * to its owner's hand: Put this card onto the battlefield from your hand tapped
 * and attacking."
 * <p>
 * 702.47b The card with ninjutsu remains revealed from the time the ability is
 * announced until the ability leaves the stack.
 * <p>
 * 702.47c A ninjutsu ability may be activated only while a creature on the
 * battlefield is unblocked (see rule 509.1h). The creature with ninjutsu is put
 * onto the battlefield unblocked. It will be attacking the same player or
 * planeswalker as the creature that was returned to its owner's hand.
 *
 * @author LevelX2
 */
public class NinjutsuAbility extends ActivatedAbilityImpl {

    private final boolean commander;

    /**
     * @param manaString ninjutsu mana cost
     */
    public NinjutsuAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString), false);
    }

    public NinjutsuAbility(Cost cost, boolean commander) {
        super(commander ? Zone.ALL : Zone.HAND, new NinjutsuEffect(), cost);
        this.addCost(new RevealNinjutsuCardCost(commander));
        this.addCost(new ReturnAttackerToHandTargetCost());
        this.commander = commander;
    }

    private NinjutsuAbility(final NinjutsuAbility ability) {
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

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("unblocked attacker you control");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    private UUID defendingPlayerId = null;

    public ReturnAttackerToHandTargetCost() {
        this.addTarget(new TargetControlledPermanent(filter));
        this.text = "Return an unblocked attacker you control to hand";
    }

    public ReturnAttackerToHandTargetCost(ReturnAttackerToHandTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.ReturnToHand, controllerId, source.getSourceId(), game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                Player controller = game.getPlayer(controllerId);
                if (permanent == null
                        || controller == null) {
                    return false;
                }
                defendingPlayerId = game.getCombat().getDefenderId(permanent.getId());
                paid |= controller.moveCardToHandWithInfo(permanent, source, game, true);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(source.getSourceId(), controllerId, game);
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
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);

        // used from hand
        Card card = player.getHand().get(ability.getSourceId(), game);

        // rules:
        // Commander ninjutsu is a variant of ninjutsu that can be activated from the command zone as
        // well as from your hand. Just as with regular ninjutsu, the Ninja enters attacking the player
        // or planeswalker that the returned creature was attacking.

        // used from command zone
        // must search all card sides for ability (example: mdf card with Ninjutsu in command zone)
        if (card == null
                && commander
                && game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true).contains(ability.getSourceId())) {
            for (CommandObject coj : game.getState().getCommand()) {
                if (coj != null && coj.getId().equals(ability.getSourceId())) {
                    if (CardUtil.getObjectParts(coj).contains(ability.getSourceId())) {
                        card = game.getCard(CardUtil.getMainCardId(game, ability.getSourceId()));
                        break;
                    }
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
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public RevealNinjutsuCardCost copy() {
        return new RevealNinjutsuCardCost(this);
    }
}
