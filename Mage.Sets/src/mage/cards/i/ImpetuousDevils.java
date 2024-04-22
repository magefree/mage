
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
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
public final class ImpetuousDevils extends CardImpl {

    public ImpetuousDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Impetuous Devils attacks, up to one target creature defending player controls blocks it this combat if able.
        this.addAbility(new ImpetuousDevilsAbility());

        // At the beginning of the end step, sacrifice Impetuous Devils.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.NEXT, false));
    }

    private ImpetuousDevils(final ImpetuousDevils card) {
        super(card);
    }

    @Override
    public ImpetuousDevils copy() {
        return new ImpetuousDevils(this);
    }
}

class ImpetuousDevilsAbility extends TriggeredAbilityImpl {

    public ImpetuousDevilsAbility() {
        super(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(Duration.EndOfCombat), false);
    }

    private ImpetuousDevilsAbility(final ImpetuousDevilsAbility ability) {
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
            TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1, filter, false);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} attacks, up to one target creature defending player controls blocks it this combat if able.";
    }

    @Override
    public ImpetuousDevilsAbility copy() {
        return new ImpetuousDevilsAbility(this);
    }
}
