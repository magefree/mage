package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InsectToken;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwarmShambler extends CardImpl {

    public SwarmShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Swarm Shambler enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                "with a +1/+1 counter on it"
        ));

        // Whenever a creature you control with a +1/+1 counter on it becomes the target of a spell an opponent controls, create a 1/1 green Insect creature token.
        this.addAbility(new SwarmShamblerTriggeredAbility());

        // {1}, {T}: Put a +1/+1 counter on Swarm Shambler.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SwarmShambler(final SwarmShambler card) {
        super(card);
    }

    @Override
    public SwarmShambler copy() {
        return new SwarmShambler(this);
    }
}

class SwarmShamblerTriggeredAbility extends TriggeredAbilityImpl {

    SwarmShamblerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new InsectToken()), false);
    }

    private SwarmShamblerTriggeredAbility(final SwarmShamblerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SwarmShamblerTriggeredAbility copy() {
        return new SwarmShamblerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell sourceObject = game.getSpell(event.getSourceId());
        Permanent permanent = game.getPermanent(event.getTargetId());
        return sourceObject != null
                && permanent != null
                && StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1.match(permanent, getSourceId(), getControllerId(), game)
                && StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS.match(sourceObject, getSourceId(), getControllerId(), game);
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with a +1/+1 counter on it " +
                "becomes the target of a spell an opponent controls, create a 1/1 green Insect creature token.";
    }
}
