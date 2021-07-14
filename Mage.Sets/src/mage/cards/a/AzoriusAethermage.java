package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AzoriusAethermage extends CardImpl {

    private static final String rule = "Whenever a permanent is returned to your hand, ";

    public AzoriusAethermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a permanent is returned to your hand, you may pay {1}. If you do, draw a card.
        Effect effect = new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{1}"));
        this.addAbility(new AzoriusAEthermageAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.HAND, effect, new FilterPermanent(), rule, false));
    }

    private AzoriusAethermage(final AzoriusAethermage card) {
        super(card);
    }

    @Override
    public AzoriusAethermage copy() {
        return new AzoriusAethermage(this);
    }
}

class AzoriusAEthermageAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected Zone fromZone;
    protected Zone toZone;
    protected String rule;

    public AzoriusAEthermageAbility(Zone zone, Zone fromZone, Zone toZone, Effect effect, FilterPermanent filter, String rule, boolean optional) {
        super(zone, effect, optional);
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.rule = rule;
        this.filter = filter;
    }

    public AzoriusAEthermageAbility(final AzoriusAEthermageAbility ability) {
        super(ability);
        this.fromZone = ability.fromZone;
        this.toZone = ability.toZone;
        this.rule = ability.rule;
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if ((fromZone == null || zEvent.getFromZone() == fromZone)
                && (toZone == null || zEvent.getToZone() == toZone)) {
            Permanent permanentThatMoved = null;
            if (zEvent.getTarget() != null) {
                permanentThatMoved = zEvent.getTarget();
            }
            //The controller's hand is where the permanent moved to.
            return permanentThatMoved != null
                    && filter.match(permanentThatMoved, sourceId, controllerId, game)
                    && zEvent.getPlayerId().equals(controllerId);
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return rule ;
    }

    @Override
    public AzoriusAEthermageAbility copy() {
        return new AzoriusAEthermageAbility(this);
    }

    public Zone getFromZone() {
        return fromZone;
    }

    public Zone getToZone() {
        return toZone;
    }
}
