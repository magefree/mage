package mage.cards.i;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

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

class IngeniousArtilleristTriggeredAbility extends TriggeredAbilityImpl {

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
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent.getToZone() != Zone.BATTLEFIELD
                || !this.controllerId.equals(event.getPlayerId())) {
            return false;
        }
        int artifacts = Stream.concat(
                zEvent.getTokens()
                        .stream(),
                zEvent.getCards()
                        .stream()
                        .map(MageItem::getId)
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
        ).filter(permanent -> permanent.isArtifact(game)).mapToInt(x -> 1).sum();
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
