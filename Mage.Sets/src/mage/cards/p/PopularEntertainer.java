package mage.cards.p;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PopularEntertainer extends CardImpl {

    public PopularEntertainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever one or more creatures you control deal combat damage to a player, goad target creature that player controls."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new PopularEntertainerAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private PopularEntertainer(final PopularEntertainer card) {
        super(card);
    }

    @Override
    public PopularEntertainer copy() {
        return new PopularEntertainer(this);
    }
}

class PopularEntertainerAbility extends TriggeredAbilityImpl {

    private final List<UUID> damagedPlayerIds = new ArrayList<>();

    PopularEntertainerAbility() {
        super(Zone.BATTLEFIELD, new GoadTargetEffect(), false);
    }

    private PopularEntertainerAbility(final PopularEntertainerAbility ability) {
        super(ability);
    }

    @Override
    public PopularEntertainerAbility copy() {
        return new PopularEntertainerAbility(this);
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
                || damagedPlayerIds.contains(event.getTargetId())) {
            return false;
        }
        damagedPlayerIds.add(event.getTargetId());
        FilterPermanent filter = new FilterCreaturePermanent(
                "creature controlled by " + game.getPlayer(event.getTargetId()).getName()
        );
        filter.add(new ControllerIdPredicate(event.getTargetId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage " +
                "to a player, goad target creature that player controls.";
    }
}
