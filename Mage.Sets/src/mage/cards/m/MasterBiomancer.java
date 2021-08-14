
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MasterBiomancer extends CardImpl {

    public MasterBiomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Each other creature you control enters the battlefield with a number of additional +1/+1 counters on it equal to Master Biomancer's power and as a Mutant in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new MasterBiomancerEntersBattlefieldEffect()));
    }

    private MasterBiomancer(final MasterBiomancer card) {
        super(card);
    }

    @Override
    public MasterBiomancer copy() {
        return new MasterBiomancer(this);
    }
}

class MasterBiomancerEntersBattlefieldEffect extends ReplacementEffectImpl {

    MasterBiomancerEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control enters the battlefield with a number of additional +1/+1 counters on it equal to {this}'s power and as a Mutant in addition to its other types";
    }

    private MasterBiomancerEntersBattlefieldEffect(MasterBiomancerEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (sourceCreature != null && creature != null) {
            int power = sourceCreature.getPower().getValue();
            if (power > 0) {
                creature.addCounters(CounterType.P1P1.createInstance(power), source.getControllerId(), source, game);
            }
            ContinuousEffect effect = new AddCardSubTypeTargetEffect(SubType.MUTANT, Duration.Custom);
            effect.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game) + 1));
            game.addEffect(effect, source);
        }
        return false;
    }

    @Override
    public MasterBiomancerEntersBattlefieldEffect copy() {
        return new MasterBiomancerEntersBattlefieldEffect(this);
    }
}
