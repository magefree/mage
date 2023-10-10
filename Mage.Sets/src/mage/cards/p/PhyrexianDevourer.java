
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PhyrexianDevourer extends CardImpl {

    public PhyrexianDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Phyrexian Devourer's power is 7 or greater, sacrifice it.
        this.addAbility(new PhyrexianDevourerStateTriggeredAbility());

        // Exile the top card of your library: Put X +1/+1 counters on Phyrexian Devourer, where X is the exiled card's converted mana cost.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhyrexianDevourerEffect(), new ExileTopCardLibraryCost()));

    }

    private PhyrexianDevourer(final PhyrexianDevourer card) {
        super(card);
    }

    @Override
    public PhyrexianDevourer copy() {
        return new PhyrexianDevourer(this);
    }
}

class PhyrexianDevourerStateTriggeredAbility extends StateTriggeredAbility {

    public PhyrexianDevourerStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    private PhyrexianDevourerStateTriggeredAbility(final PhyrexianDevourerStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PhyrexianDevourerStateTriggeredAbility copy() {
        return new PhyrexianDevourerStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getPower().getValue() >= 7;
    }

    @Override
    public String getRule() {
        return "When {this}'s power is 7 or greater, sacrifice it.";
    }

}

class PhyrexianDevourerEffect extends OneShotEffect {

    public PhyrexianDevourerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put X +1/+1 counters on {this}, where X is the exiled card's mana value";
    }

    private PhyrexianDevourerEffect(final PhyrexianDevourerEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianDevourerEffect copy() {
        return new PhyrexianDevourerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = null;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof ExileTopCardLibraryCost) {
                    card = ((ExileTopCardLibraryCost) cost).getCard();
                }
            }
            if (card != null) {
                int amount = card.getManaValue();
                if (amount > 0) {
                    return new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount)).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}

class ExileTopCardLibraryCost extends CostImpl {

    Card card;

    public ExileTopCardLibraryCost() {
        this.text = "Exile the top card of your library";
    }

    private ExileTopCardLibraryCost(final ExileTopCardLibraryCost cost) {
        super(cost);
        this.card = cost.getCard();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                paid = controller.moveCards(card, Zone.EXILED, ability, game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return controller.getLibrary().hasCards();
        }
        return false;
    }

    @Override
    public ExileTopCardLibraryCost copy() {
        return new ExileTopCardLibraryCost(this);
    }

    public Card getCard() {
        return card;
    }

}
