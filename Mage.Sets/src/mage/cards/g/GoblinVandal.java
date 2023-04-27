
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class GoblinVandal extends CardImpl {

    public GoblinVandal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Goblin Vandal attacks and isn't blocked, you may pay {R}. If you do, destroy target artifact defending player controls and Goblin Vandal assigns no combat damage this turn.
        Effect effect = new DestroyTargetEffect();
        effect.setText("destroy target artifact defending player controls");
        DoIfCostPaid effect2 = new DoIfCostPaid(effect, new ManaCostsImpl<>("{R}"), "Pay {R} to destroy artifact of defending player?");
        effect = new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn);
        effect.setText("and {this} assigns no combat damage this turn");
        effect2.addEffect(effect);
        this.addAbility(new GoblinVandalTriggeredAbility(effect2));

    }

    private GoblinVandal(final GoblinVandal card) {
        super(card);
    }

    @Override
    public GoblinVandal copy() {
        return new GoblinVandal(this);
    }
}

class GoblinVandalTriggeredAbility extends TriggeredAbilityImpl {

    public GoblinVandalTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false );
    }

    public GoblinVandalTriggeredAbility(final GoblinVandalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoblinVandalTriggeredAbility copy() {
        return new GoblinVandalTriggeredAbility(this);
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
                    UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
                    FilterPermanent filter = new FilterArtifactPermanent();
                    filter.add(new ControllerIdPredicate(defendingPlayerId));
                    Target target = new TargetPermanent(filter);
                    this.addTarget(target);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks and isn't blocked, you may pay {R}. If you do, destroy target artifact defending player controls and {this} assigns no combat damage this turn";
    }
}
