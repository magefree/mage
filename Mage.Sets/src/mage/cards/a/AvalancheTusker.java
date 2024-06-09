
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AvalancheTusker extends CardImpl {

    public AvalancheTusker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{U}{R}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Whenever Avalanche Tusker attacks, target creature defending player controls blocks it this turn if able.
        this.addAbility(new AvalancheTuskerAbility());
    }

    private AvalancheTusker(final AvalancheTusker card) {
        super(card);
    }

    @Override
    public AvalancheTusker copy() {
        return new AvalancheTusker(this);
    }
}

class AvalancheTuskerAbility extends TriggeredAbilityImpl {

    public AvalancheTuskerAbility() {
        super(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(Duration.EndOfCombat), false);
    }

    private AvalancheTuskerAbility(final AvalancheTuskerAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefendingPlayerId(sourceId, game);
            filter.add(new ControllerIdPredicate(defenderId));

            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, target creature defending player controls blocks it this combat if able.";
    }

    @Override
    public AvalancheTuskerAbility copy() {
        return new AvalancheTuskerAbility(this);
    }
}
