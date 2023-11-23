package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 * @author
 */
public final class ScionOfCalamity extends CardImpl {

    public ScionOfCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Myriad
        this.addAbility(new MyriadAbility(true));

        // Whenever Scion of Calamity deals combat damage to a player, destroy target artifact or enchantment that player controls.
        this.addAbility(new ScionOfCalamityTriggeredAbility());
    }

    private ScionOfCalamity(final ScionOfCalamity card) {
        super(card);
    }

    @Override
    public ScionOfCalamity copy() {
        return new ScionOfCalamity(this);
    }
}

class ScionOfCalamityTriggeredAbility extends TriggeredAbilityImpl {

    ScionOfCalamityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    private ScionOfCalamityTriggeredAbility(final ScionOfCalamityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScionOfCalamityTriggeredAbility copy() {
        return new ScionOfCalamityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                FilterPermanent filter = new FilterPermanent("an artifact or enchantment controlled by " + player.getLogName());
                filter.add(Predicates.or(
                        CardType.ARTIFACT.getPredicate(),
                        CardType.ENCHANTMENT.getPredicate()));
                filter.add(new ControllerIdPredicate(event.getTargetId()));

                this.getTargets().clear();
                this.addTarget(new TargetPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, "
                + "destroy target artifact or enchantment that player controls.";
    }
}
