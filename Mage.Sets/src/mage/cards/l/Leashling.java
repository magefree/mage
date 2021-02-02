
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Quercitron
 */
public final class Leashling extends CardImpl {

    public Leashling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Put a card from your hand on top of your library: Return Leashling to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(), new PutCardFromHandOnTopOfLibrary()));
    }

    private Leashling(final Leashling card) {
        super(card);
    }

    @Override
    public Leashling copy() {
        return new Leashling(this);
    }
}

class PutCardFromHandOnTopOfLibrary extends CostImpl {

    protected final int amount;

    public PutCardFromHandOnTopOfLibrary() {
        this(1);
    }

    public PutCardFromHandOnTopOfLibrary(final int amount) {
        this.amount = amount;
        this.text = "put " + (amount == 1 ? "a card" : (amount + " cards")) + " from your hand on top of your library";
    }

    public PutCardFromHandOnTopOfLibrary(final PutCardFromHandOnTopOfLibrary cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public Cost copy() {
        return new PutCardFromHandOnTopOfLibrary(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return !controller.getHand().isEmpty();
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetCardInHand target = new TargetCardInHand();
            controller.chooseTarget(Outcome.ReturnToHand, target, ability, game);
            Card card = controller.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                controller.putCardsOnTopOfLibrary(new CardsImpl(card), game, ability, false);
                paid = true;
            }
        }
        return paid;
    }

}
