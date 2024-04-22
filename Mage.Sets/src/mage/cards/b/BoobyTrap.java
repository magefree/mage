package mage.cards.b;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author noahg
 */
public final class BoobyTrap extends CardImpl {

    public BoobyTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // As Booby Trap enters the battlefield, name a card other than a basic land card and choose an opponent.
        AsEntersBattlefieldAbility etbAbility = new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NOT_BASIC_LAND_NAME));
        etbAbility.addEffect(new ChooseOpponentEffect(Outcome.Damage));
        this.addAbility(etbAbility);

        // The chosen player reveals each card they draw.
        // When the chosen player draws the named card, sacrifice Booby Trap. If you do, Booby Trap deals 10 damage to that player.
        this.addAbility(new BoobyTrapTriggeredAbility());
    }

    private BoobyTrap(final BoobyTrap card) {
        super(card);
    }

    @Override
    public BoobyTrap copy() {
        return new BoobyTrap(this);
    }
}

class BoobyTrapTriggeredAbility extends TriggeredAbilityImpl {

    public BoobyTrapTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DamageTargetEffect(10, true, "that player"), new SacrificeSourceCost(), "", false), false);
    }

    private BoobyTrapTriggeredAbility(final BoobyTrapTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (event.getPlayerId() == null || game.getState() == null || controller == null) {
            return false;
        }
        if (event.getPlayerId().equals(game.getState().getValue(getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY))) {
            Card drawn = game.getCard(event.getTargetId());
            if (drawn != null) {
                controller.revealCards(this, new CardsImpl(drawn), game);
                if (drawn.getName().equals(game.getState().getValue(getSourceId().toString() + ChooseACardNameEffect.INFO_KEY))) {
                    //Set target
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public BoobyTrapTriggeredAbility copy() {
        return new BoobyTrapTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "The chosen player reveals each card they draw.\n" +
                "When the chosen player draws the named card, sacrifice {this}. If you do, {this} deals 10 damage to that player.";
    }
}