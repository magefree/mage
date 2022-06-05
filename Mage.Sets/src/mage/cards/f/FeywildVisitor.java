package mage.cards.f;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.FaerieDragonToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeywildVisitor extends CardImpl {

    public FeywildVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever one or more nontoken creatures you control deal combat damage to a player, you create a 1/1 blue Faerie Dragon creature token with flying."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new FeywildVisitorAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private FeywildVisitor(final FeywildVisitor card) {
        super(card);
    }

    @Override
    public FeywildVisitor copy() {
        return new FeywildVisitor(this);
    }
}

class FeywildVisitorAbility extends TriggeredAbilityImpl {

    private final List<UUID> damagedPlayerIds = new ArrayList<>();

    FeywildVisitorAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new FaerieDragonToken()), false);
    }

    private FeywildVisitorAbility(final FeywildVisitorAbility ability) {
        super(ability);
    }

    @Override
    public FeywildVisitorAbility copy() {
        return new FeywildVisitorAbility(this);
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
                || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature == null
                || !creature.isControlledBy(getControllerId())
                || creature instanceof PermanentToken
                || damagedPlayerIds.contains(event.getTargetId())) {
            return false;
        }
        damagedPlayerIds.add(event.getTargetId());
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever one or more nontoken creatures you control deal combat damage to a player, you";
    }
}
