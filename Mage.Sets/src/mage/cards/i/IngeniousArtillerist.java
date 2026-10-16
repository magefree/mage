package mage.cards.i;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IngeniousArtillerist extends CardImpl {

    public IngeniousArtillerist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever one or more artifacts enter the battlefield under you control, Ingenious Artillerist deals that much damage to each opponent.
        this.addAbility(new IngeniousArtilleristTriggeredAbility());
    }

    private IngeniousArtillerist(final IngeniousArtillerist card) {
        super(card);
    }

    @Override
    public IngeniousArtillerist copy() {
        return new IngeniousArtillerist(this);
    }
}

class IngeniousArtilleristTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    IngeniousArtilleristTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(
                Outcome.Benefit, SavedDamageValue.MANY, TargetController.OPPONENT
        ), false);
    }

    private IngeniousArtilleristTriggeredAbility(final IngeniousArtilleristTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getToZone() != Zone.BATTLEFIELD) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isArtifact(game) && permanent.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int artifacts = getFilteredEvents((ZoneChangeBatchEvent) event, game).size();
        if (artifacts > 0) {
            this.getEffects().setValue("damage", artifacts);
            return true;
        }
        return false;
    }

    @Override
    public IngeniousArtilleristTriggeredAbility copy() {
        return new IngeniousArtilleristTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more artifacts enter the battlefield under your control, " +
                "{this} deals that much damage to each opponent.";
    }
}
