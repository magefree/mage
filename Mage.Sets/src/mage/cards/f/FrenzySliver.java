
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class FrenzySliver extends CardImpl {


    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All sliver creatures");
    private static final FilterCreaturePermanent targetSliverFilter = new FilterCreaturePermanent("Sliver");

    static {
        filter.add(SubType.SLIVER.getPredicate());
        targetSliverFilter.add(SubType.SLIVER.getPredicate());
    }
        
    public FrenzySliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures have frenzy 1. (Whenever a Sliver attacks and isn't blocked, it gets +1/+0 until end of turn.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new FrenzyAbility(), Duration.WhileOnBattlefield,
                filter, "All Sliver creatures have frenzy 1. <i>(Whenever a Sliver attacks and isn't blocked, it gets +1/+0 until end of turn.)</i>")));
    }

    private FrenzySliver(final FrenzySliver card) {
        super(card);
    }

    @Override
    public FrenzySliver copy() {
        return new FrenzySliver(this);
    }
}

class FrenzyAbility extends TriggeredAbilityImpl {

    public FrenzyAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1,0, Duration.EndOfTurn));
    }

    private FrenzyAbility(final FrenzyAbility ability) {
        super(ability);
    }

    @Override
    public FrenzyAbility copy() {
        return new FrenzyAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(getSourceId());
        if (sourcePermanent.isAttacking()) {
            for (CombatGroup combatGroup: game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().isEmpty() && combatGroup.getAttackers().contains(getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Frenzy 1. <i>(Whenever this creature attacks and isn't blocked, it gets +1/+0 until end of turn.)</i>";
    }
}