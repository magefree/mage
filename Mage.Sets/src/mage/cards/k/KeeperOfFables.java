package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeeperOfFables extends CardImpl {

    public KeeperOfFables(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever one or more non-Human creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new KeeperOfFablesTriggeredAbility());
    }

    private KeeperOfFables(final KeeperOfFables card) {
        super(card);
    }

    @Override
    public KeeperOfFables copy() {
        return new KeeperOfFables(this);
    }
}

class KeeperOfFablesTriggeredAbility extends TriggeredAbilityImpl {

    private final List<UUID> damagedPlayerIds = new ArrayList<>();

    KeeperOfFablesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private KeeperOfFablesTriggeredAbility(final KeeperOfFablesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KeeperOfFablesTriggeredAbility copy() {
        return new KeeperOfFablesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null
                        && creature.isControlledBy(controllerId)
                        && !creature.hasSubtype(SubType.HUMAN, game)
                        && !damagedPlayerIds.contains(event.getTargetId())) {
                    damagedPlayerIds.add(event.getTargetId());
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            damagedPlayerIds.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more non-Human creatures you control deal combat damage to a player, draw a card.";
    }
}
