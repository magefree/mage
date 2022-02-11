package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProsperousThief extends CardImpl {

    public ProsperousThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ninjutsu {1}{U}
        this.addAbility(new NinjutsuAbility("{1}{U}"));

        // Whenever one or more Ninja or Rogue creatures you control deal combat damage to a player, create a Treasure token.
        this.addAbility(new ProsperousThiefTriggeredAbility());
    }

    private ProsperousThief(final ProsperousThief card) {
        super(card);
    }

    @Override
    public ProsperousThief copy() {
        return new ProsperousThief(this);
    }
}

class ProsperousThiefTriggeredAbility extends TriggeredAbilityImpl {

    private final List<UUID> damagedPlayerIds = new ArrayList<>();

    ProsperousThiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()), false);
    }

    private ProsperousThiefTriggeredAbility(final ProsperousThiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ProsperousThiefTriggeredAbility copy() {
        return new ProsperousThiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            damagedPlayerIds.clear();
            return false;
        }
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER
                || !((DamagedPlayerEvent) event).isCombatDamage()
                || damagedPlayerIds.contains(event.getTargetId())) {
            return false;
        }
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature == null
                || !isControlledBy(creature.getControllerId())
                || (!creature.hasSubtype(SubType.NINJA, game)
                && !creature.hasSubtype(SubType.ROGUE, game))) {
            return false;
        }
        damagedPlayerIds.add(event.getTargetId());
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Ninja or Rogue creatures you control " +
                "deal combat damage to a player, create a Treasure token.";
    }
}
