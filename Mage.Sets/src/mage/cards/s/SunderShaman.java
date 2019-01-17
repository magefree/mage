package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunderShaman extends CardImpl {

    public SunderShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{G}{G}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sunder Shaman can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()));

        // Whenever Sunder Shaman deals combat damage to a player, destroy target artifact or enchantment that player controls.
        this.addAbility(new SunderShamanTriggeredAbility());
    }

    private SunderShaman(final SunderShaman card) {
        super(card);
    }

    @Override
    public SunderShaman copy() {
        return new SunderShaman(this);
    }
}

class SunderShamanTriggeredAbility extends TriggeredAbilityImpl {

    SunderShamanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    private SunderShamanTriggeredAbility(final SunderShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SunderShamanTriggeredAbility copy() {
        return new SunderShamanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null && event.getSourceId().equals(this.sourceId)) {
            FilterPermanent filter = StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT.copy();
            filter.setMessage("artifact or enchantment controlled by" + opponent.getLogName());
            filter.add(new ControllerIdPredicate(opponent.getId()));
            this.getTargets().clear();
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, "
                + "destroy target artifact or enchantment that player controls.";
    }
}
