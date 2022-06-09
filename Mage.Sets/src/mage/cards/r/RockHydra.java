
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author anonymous
 */
public final class RockHydra extends CardImpl {

    public RockHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{R}{R}");
        
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        
        // Rock Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
        // For each 1 damage that would be dealt to Rock Hydra, if it has a +1/+1 counter on it, remove a +1/+1 counter from it and prevent that 1 damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RockHydraEffect()));
        // {R}: Prevent the next 1 damage that would be dealt to Rock Hydra this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToSourceEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{R}")));
        // {R}{R}{R}: Put a +1/+1 counter on Rock Hydra. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new ManaCostsImpl<>("{R}{R}{R}"), new IsStepCondition(PhaseStep.UPKEEP), null));
    }

    private RockHydra(final RockHydra card) {
        super(card);
    }

    @Override
    public RockHydra copy() {
        return new RockHydra(this);
    }
    
    static class RockHydraEffect extends PreventionEffectImpl {

        public RockHydraEffect() {
            super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
            staticText = "For each 1 damage that would be dealt to {this}, if it has a +1/+1 counter on it, remove a +1/+1 counter from it and prevent that 1 damage.";
        }

        public RockHydraEffect(final RockHydraEffect effect) {
            super(effect);
        }

        @Override
        public RockHydraEffect copy() {
            return new RockHydraEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            int damage = event.getAmount();
            preventDamageAction(event, source, game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.removeCounters(CounterType.P1P1.createInstance(damage), source, game); //MTG ruling Rock Hydra loses counters even if the damage isn't prevented
            }
            return false;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            if (super.applies(event, source, game)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
            return false;
        }

    }
}