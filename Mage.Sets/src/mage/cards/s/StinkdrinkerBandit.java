
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BursegSardaukar
 */
public final class StinkdrinkerBandit extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Rogue");

    static {
        filter.add(new SubtypePredicate(SubType.ROGUE));
    }

    public StinkdrinkerBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowl {1}, {B} (You may cast this for its prowl cost if you dealt combat damage to a player this turn with a Goblin or Rogue.)
        this.addAbility(new ProwlAbility(this, "{1}{B}"));

        // Whenever a Rogue you control attacks and isn't blocked, it gets +2/+1 until end of turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new StinkdrinkerBanditTriggeredAbility(), Duration.WhileOnBattlefield,
                filter, "Whenever a Rogue you control attacks and isn't blocked, it gets +2/+1 until end of turn")));
    }

    public StinkdrinkerBandit(final StinkdrinkerBandit card) {
        super(card);
    }

    @Override
    public StinkdrinkerBandit copy() {
        return new StinkdrinkerBandit(this);
    }
}

class StinkdrinkerBanditTriggeredAbility extends TriggeredAbilityImpl {

    public StinkdrinkerBanditTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(2, 1, Duration.EndOfTurn));
    }

    public StinkdrinkerBanditTriggeredAbility(final StinkdrinkerBanditTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StinkdrinkerBanditTriggeredAbility copy() {
        return new StinkdrinkerBanditTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(getSourceId());
        if (sourcePermanent.isAttacking()) {
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().isEmpty()
                        && combatGroup.getDefenderId().equals(event.getTargetId())
                        && combatGroup.getAttackers().contains(getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Rogue you control attacks and isn't blocked, it gets +2/+1 until end of turn.";
    }
}
