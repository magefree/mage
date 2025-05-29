package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.counter.MoveCounterTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RikkuResourcefulGuardian extends CardImpl {

    public RikkuResourcefulGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you put one or more counters on a creature, until end of turn, that creature can't be blocked by creatures your opponents control.
        this.addAbility(new RikkuResourcefulGuardianTriggeredAbility());

        // Steal -- {1}, {T}: Move a counter from target creature an opponent controls onto target creature you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new MoveCounterTargetsEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Steal"));
    }

    private RikkuResourcefulGuardian(final RikkuResourcefulGuardian card) {
        super(card);
    }

    @Override
    public RikkuResourcefulGuardian copy() {
        return new RikkuResourcefulGuardian(this);
    }
}

class RikkuResourcefulGuardianTriggeredAbility extends TriggeredAbilityImpl {

    RikkuResourcefulGuardianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CantBeBlockedByAllTargetEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, Duration.EndOfTurn
        ).setText("until end of turn, that creature can't be blocked by creatures your opponents control"));
        this.setTriggerPhrase("Whenever you put one or more counters on a creature, ");
    }

    private RikkuResourcefulGuardianTriggeredAbility(final RikkuResourcefulGuardianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RikkuResourcefulGuardianTriggeredAbility copy() {
        return new RikkuResourcefulGuardianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId())
                && Optional
                .ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getPermanent)
                .map(permanent -> permanent.isCreature(game))
                .orElse(false);
    }
}
