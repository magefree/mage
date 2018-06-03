
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class KeyToTheCity extends CardImpl {

    public KeyToTheCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {T}, Discard a card: Up to one target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(), new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever Key to the City becomes untapped, you may pay {2}. If you do, draw a card.
        this.addAbility(new KeyToTheCityTriggeredAbility());
    }

    public KeyToTheCity(final KeyToTheCity card) {
        super(card);
    }

    @Override
    public KeyToTheCity copy() {
        return new KeyToTheCity(this);
    }
}

class KeyToTheCityTriggeredAbility extends TriggeredAbilityImpl{

    KeyToTheCityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new GenericManaCost(2)));
    }

    KeyToTheCityTriggeredAbility(final KeyToTheCityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KeyToTheCityTriggeredAbility copy() {
        return new KeyToTheCityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever Key to the City becomes untapped, you may pay {2}. If you do, draw a card.";
    }

}
