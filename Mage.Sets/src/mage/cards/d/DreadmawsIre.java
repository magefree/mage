package mage.cards.d;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DreadmawsIre extends CardImpl {

    public DreadmawsIre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Until end of turn, target attacking creature gets +2/+2 and gains trample and "Whenever this creature deals combat damage to a player, destroy target artifact that player controls."
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2)
                .setText("Until end of turn, target attacking creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample"));

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DreadmawsIreTriggeredAbility()
        ).setText("and \"Whenever this creature deals combat damage to a player, destroy target artifact that player controls.\""));
    }

    private DreadmawsIre(final DreadmawsIre card) {
        super(card);
    }

    @Override
    public DreadmawsIre copy() {
        return new DreadmawsIre(this);
    }
}

/**
 * Inspired by {@link mage.cards.t.TrygonPredator}
 */
class DreadmawsIreTriggeredAbility extends TriggeredAbilityImpl {

    public DreadmawsIreTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    private DreadmawsIreTriggeredAbility(final DreadmawsIreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DreadmawsIreTriggeredAbility copy() {
        return new DreadmawsIreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.sourceId) || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }

        Player player = game.getPlayer(event.getTargetId());
        if (player == null) {
            return false;
        }

        FilterPermanent filter = new FilterArtifactPermanent("an artifact controlled by " + player.getLogName());
        filter.add(new ControllerIdPredicate(event.getTargetId()));

        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, destroy target artifact that player controls.";
    }
}
