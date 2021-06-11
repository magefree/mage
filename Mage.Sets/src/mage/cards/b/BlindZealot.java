package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class BlindZealot extends CardImpl {

    public BlindZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN, SubType.HUMAN, SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());

        // Whenever Blind Zealot deals combat damage to a player, you may sacrifice it. If you do, destroy target creature that player controls.
        Ability ability = new BlindZealotTriggeredAbility();
        this.addAbility(ability);
    }

    private BlindZealot(final BlindZealot card) {
        super(card);
    }

    @Override
    public BlindZealot copy() {
        return new BlindZealot(this);
    }
}

class BlindZealotTriggeredAbility extends TriggeredAbilityImpl {

    BlindZealotTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DestroyTargetEffect(), new SacrificeSourceCost()), false);
    }

    private BlindZealotTriggeredAbility(final BlindZealotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlindZealotTriggeredAbility copy() {
        return new BlindZealotTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent == null
                || !event.getSourceId().equals(this.sourceId)
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
        filter.add(new ControllerIdPredicate(opponent.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetCreaturePermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may sacrifice it. "
                + "If you do, destroy target creature that player controls.";
    }
}
