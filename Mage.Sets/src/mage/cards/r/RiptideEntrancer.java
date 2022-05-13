package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.MorphAbility;
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
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiptideEntrancer extends CardImpl {

    public RiptideEntrancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Riptide Entrancer deals combat damage to a player, you may sacrifice it. If you do, gain control of target creature that player controls.
        this.addAbility(new RiptideEntrancerTriggeredAbility());

        // Morph {U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{U}{U}")));
    }

    private RiptideEntrancer(final RiptideEntrancer card) {
        super(card);
    }

    @Override
    public RiptideEntrancer copy() {
        return new RiptideEntrancer(this);
    }
}

class RiptideEntrancerTriggeredAbility extends TriggeredAbilityImpl {

    public RiptideEntrancerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(
                new GainControlTargetEffect(Duration.Custom),
                new SacrificeSourceCost()
        ), false);
    }

    public RiptideEntrancerTriggeredAbility(final RiptideEntrancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RiptideEntrancerTriggeredAbility copy() {
        return new RiptideEntrancerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null && event.getSourceId().equals(this.sourceId)) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
            filter.add(new ControllerIdPredicate(opponent.getId()));
            this.getTargets().clear();
            this.addTarget(new TargetCreaturePermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may sacrifice it. "
                + "If you do, gain control of target creature that player controls";
    }
}
