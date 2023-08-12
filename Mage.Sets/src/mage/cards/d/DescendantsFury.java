package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.TargetPointer;
import mage.watchers.common.DamagedPlayerThisCombatWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DescendantsFury extends CardImpl {

    public DescendantsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever one or more creatures you control deal combat damage to a player, you may sacrifice one of them. If you do, reveal cards from the top of your library until you reveal a creature card that shares a creature type with the sacrificed creature. Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        Ability ability = new DealCombatDamageControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(
                        new DescendantsFuryEffect(),
                        new DescendantsFurySacrificeCost()
                ),
                true
        );

        ability.addWatcher(new DamagedPlayerThisCombatWatcher());
        this.addAbility(ability);
    }

    private DescendantsFury(final DescendantsFury card) {
        super(card);
    }

    @Override
    public DescendantsFury copy() {
        return new DescendantsFury(this);
    }
}

class DescendantsFurySacrificeCost extends CostImpl implements SacrificeCost {
    DescendantsFurySacrificeCost() {
        this.text = "sacrifice one of them";
    }

    private DescendantsFurySacrificeCost(final DescendantsFurySacrificeCost cost) {
        super(cost);
    }

    @Override
    public DescendantsFurySacrificeCost copy() {
        return new DescendantsFurySacrificeCost(this);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        DamagedPlayerThisCombatWatcher watcher = game.getState().getWatcher(DamagedPlayerThisCombatWatcher.class);
        if (watcher == null) {
            return false;
        }
        TargetPointer targetPointer = source.getEffects().get(0).getTargetPointer();
        if (targetPointer == null) {
            return false;
        }
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || damagedPlayer == null) {
            return false;
        }

        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(new PermanentReferenceInCollectionPredicate(
                watcher.getPermanents(controller.getId(), damagedPlayer.getId())));

        TargetControlledPermanent target = new TargetControlledPermanent(0, 1, filter, true);

        if (!controller.choose(Outcome.Sacrifice, target, source, game)) {
            return false;
        }

        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        if (permanent.sacrifice(source, game)) {
            source.getEffects().setValue("SACRIFICED_PERMANENT", permanent);
            return true;
        }

        return false;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        DamagedPlayerThisCombatWatcher watcher = game.getState().getWatcher(DamagedPlayerThisCombatWatcher.class);
        if (watcher == null) {
            return false;
        }
        TargetPointer targetPointer = source.getEffects().get(0).getTargetPointer();
        if (targetPointer == null) {
            return false;
        }
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || damagedPlayer == null) {
            return false;
        }

        return watcher.getPermanents(controller.getId(), damagedPlayer.getId())
                      .stream()
                      .map(p -> p.getPermanent(game))
                      .anyMatch(p -> p != null && controller.canPaySacrificeCost(p, source, controllerId, game));
    }
}


class DescendantsFuryEffect extends OneShotEffect {

    DescendantsFuryEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal cards from the top of your library until you reveal a creature card that "
                + "shares a creature type with the sacrificed creature. Put that card onto the battlefield "
                + "and the rest on the bottom of your library in a random order";
    }

    private DescendantsFuryEffect(final DescendantsFuryEffect effect) {
        super(effect);
    }

    @Override
    public DescendantsFuryEffect copy() {
        return new DescendantsFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("SACRIFICED_PERMANENT");
        if (permanent == null) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Cards revealed = new CardsImpl();
        Cards otherCards = new CardsImpl();
        for (Card card : controller.getLibrary().getCards(game)) {
            revealed.add(card);
            if (card != null && card.isCreature(game) && permanent.shareCreatureTypes(game, card)) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                break;
            } else {
                otherCards.add(card);
            }
        }
        controller.revealCards(source, revealed, game);
        controller.putCardsOnBottomOfLibrary(otherCards, game, source, false);
        return true;
    }
}