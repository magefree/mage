package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noahg
 */
public final class HeartWolf extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.DWARF.getPredicate());
    }

    public HeartWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {tap}: Target Dwarf creature gets +2/+0 and gains first strike until end of turn. When that creature leaves the battlefield this turn, sacrifice Heart Wolf. Activate this ability only during combat.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("Target Dwarf creature gets +2/+0"), new TapSourceCost(), new IsPhaseCondition(TurnPhase.COMBAT));
        ability.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(),
                Duration.EndOfTurn).setText("and gains first strike until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new HeartWolfDelayedTriggeredAbility(), true));
        this.addAbility(ability);
    }

    private HeartWolf(final HeartWolf card) {
        super(card);
    }

    @Override
    public HeartWolf copy() {
        return new HeartWolf(this);
    }
}

class HeartWolfDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public HeartWolfDelayedTriggeredAbility() {
        super(new SacrificeSourceEffect(), Duration.EndOfTurn, false);
    }

    private HeartWolfDelayedTriggeredAbility(final HeartWolfDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getTarget() != null && zEvent.getTargetId().equals(getTargets().getFirstTarget())) {
            this.getTargets().clear(); // else spell fizzles because target creature died
            return true;
        }
        return false;
    }

    @Override
    public HeartWolfDelayedTriggeredAbility copy() {
        return new HeartWolfDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature leaves the battlefield this turn, sacrifice {this}.";
    }
}