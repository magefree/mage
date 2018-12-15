package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class AetherTide extends CardImpl {

    public AetherTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");

        // As an additional cost to cast Aether Tide, discard X creature cards.
        this.getSpellAbility().addCost(new AetherTideCost());

        // Return X target creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetPermanentEffect());

    }

    public AetherTide(final AetherTide card) {
        super(card);
    }

    @Override
    public AetherTide copy() {
        return new AetherTide(this);
    }
}

class AetherTideCost extends VariableCostImpl {

    public AetherTideCost() {
        super("discard X creature cards");
        text = "As an additional cost to cast {this}, discard X creature cards";
    }

    public AetherTideCost(AetherTideCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return (game.getPlayer(controllerId).getHand().count(new FilterCreatureCard(), game) > 0);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.getHand().count(new FilterCreatureCard(), game);
        }
        return 0;
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return 0;
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetCardInHand target = new TargetCardInHand(xValue, new FilterCreatureCard());
        return new DiscardTargetCost(target);
    }

    @Override
    public int announceXValue(Ability source, Game game) {
        int xValue = 0;
        Player controller = game.getPlayer(source.getControllerId());
        StackObject stackObject = game.getStack().getStackObject(source.getId());
        if (controller != null
                && stackObject != null) {
            xValue = controller.announceXCost(getMinValue(source, game), getMaxValue(source, game),
                    "Announce the number of creature cards to discard", game, source, this);
        }
        return xValue;
    }

    @Override
    public AetherTideCost copy() {
        return new AetherTideCost(this);
    }

}

class ReturnToHandTargetPermanentEffect extends OneShotEffect {

    public ReturnToHandTargetPermanentEffect() {
        super(Outcome.ReturnToHand);
        setText("Return X target creatures to their owners' hands");
    }

    public ReturnToHandTargetPermanentEffect(final ReturnToHandTargetPermanentEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandTargetPermanentEffect copy() {
        return new ReturnToHandTargetPermanentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int xPaid = source.getCosts().getVariableCosts().get(0).getAmount();
        if (controller != null
                && xPaid > 0) {
            int available = game.getBattlefield().count(new FilterCreaturePermanent(),
                    source.getSourceId(),
                    source.getControllerId(), game);
            if (available > 0) {
                TargetPermanent target = new TargetPermanent(Math.min(xPaid, available),
                        xPaid,
                        new FilterCreaturePermanent("creatures to return to their owner's hands"),
                        true);
                if (controller.chooseTarget(outcome.Detriment, target, source, game)) {
                    controller.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
