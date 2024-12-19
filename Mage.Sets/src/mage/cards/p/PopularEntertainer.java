package mage.cards.p;

import mage.abilities.BatchTriggeredAbility;
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
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PopularEntertainer extends CardImpl {

    public PopularEntertainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
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

class PopularEntertainerAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

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
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        if (!event.isCombatDamage() || event.getAmount() <= 0) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null && isControlledBy(permanent.getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        if (player == null || getFilteredEvents((DamagedBatchForOnePlayerEvent) event, game).isEmpty()) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent(
                "creature controlled by " + player.getName()
        );
        filter.add(new ControllerIdPredicate(player.getId()));
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
