package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReveilleSquad extends CardImpl {

    public ReveilleSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more creatures attack you, if Reveille Squad is untapped, you may untap all creatures you control.
        this.addAbility(new ReveilleSquadTriggeredAbility());
    }

    private ReveilleSquad(final ReveilleSquad card) {
        super(card);
    }

    @Override
    public ReveilleSquad copy() {
        return new ReveilleSquad(this);
    }
}

class ReveilleSquadTriggeredAbility extends TriggeredAbilityImpl {

    public ReveilleSquadTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapAllControllerEffect(new FilterCreaturePermanent("all creatures you control")), true);
    }

    public ReveilleSquadTriggeredAbility(final ReveilleSquadTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ReveilleSquadTriggeredAbility copy() {
        return new ReveilleSquadTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (UUID attackerId : game.getCombat().getAttackers()) {
            if (game.getCombat().getDefenderId(attackerId).equals(controllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return SourceTappedCondition.UNTAPPED.apply(game, this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures attack you, if {this} is untapped, you may untap all creatures you control.";
    }
}
