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
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AzoriusAethermage extends CardImpl {

    public AzoriusAethermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a permanent is returned to your hand, you may pay {1}. If you do, draw a card.
        Effect effect = new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}"));
        this.addAbility(new AzoriusAEthermageAbility(effect));
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

    private static final String rule = "Whenever a permanent is returned to your hand, ";

    public AzoriusAEthermageAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    private AzoriusAEthermageAbility(final AzoriusAEthermageAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() != Zone.BATTLEFIELD || zEvent.getToZone() != Zone.HAND) {
            return false;
        }

        if (!zEvent.getPlayerId().equals(controllerId)) {
            return false;
        }

        Permanent permanentThatMoved = zEvent.getTarget();
        if (permanentThatMoved == null) {
            return false;
        }

        return StaticFilters.FILTER_PERMANENT_CREATURE.match(permanentThatMoved, controllerId, this, game);
    }

    @Override
    public String getTriggerPhrase() {
        return rule;
    }

    @Override
    public AzoriusAEthermageAbility copy() {
        return new AzoriusAEthermageAbility(this);
    }
}
