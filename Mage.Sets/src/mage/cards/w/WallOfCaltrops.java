
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class WallOfCaltrops extends CardImpl {

    public WallOfCaltrops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Caltrops blocks a creature, if at least one other Wall creature is blocking that creature and no non-Wall creatures are blocking that creature, Wall of Caltrops gains banding until end of turn.
        this.addAbility(new WallOfCaltropsAbility());
    }

    private WallOfCaltrops(final WallOfCaltrops card) {
        super(card);
    }

    @Override
    public WallOfCaltrops copy() {
        return new WallOfCaltrops(this);
    }
}

class WallOfCaltropsAbility extends BlocksCreatureTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall creature");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public WallOfCaltropsAbility() {
        super(new GainAbilitySourceEffect(BandingAbility.getInstance(), Duration.EndOfTurn));
    }

    public WallOfCaltropsAbility(WallOfCaltropsAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null
                    && targetPermanent.isCreature(game)) {
                CombatGroup group = game.getCombat().findGroup(targetPermanent.getId());
                if (group != null) {
                    for (UUID blockerId : group.getBlockers()) {
                        Permanent blocker = game.getPermanent(blockerId);
                        if (blocker != null) {
                            if (!filter.match(blocker, game)) {
                                return false;
                            }
                        }
                    }
                    return group.getBlockers().size() > 1 && group.getBlockers().contains(sourceId);
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks a creature, if at least one other Wall creature is blocking that creature and no non-Wall creatures are blocking that creature, {this} gains banding until end of turn.";
    }

    @Override
    public WallOfCaltropsAbility copy() {
        return new WallOfCaltropsAbility(this);
    }
}
