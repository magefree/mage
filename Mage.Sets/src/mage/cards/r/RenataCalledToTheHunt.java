package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RenataCalledToTheHunt extends CardImpl {

    public RenataCalledToTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Renata's power is equal to your devotion to green.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(DevotionCount.G)
                        .setText("{this}'s power is equal to your devotion to green")
        ).addHint(DevotionCount.G.getHint()));

        // Each other creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new RenataCalledToTheHuntReplacementEffect()));
    }

    private RenataCalledToTheHunt(final RenataCalledToTheHunt card) {
        super(card);
    }

    @Override
    public RenataCalledToTheHunt copy() {
        return new RenataCalledToTheHunt(this);
    }
}

class RenataCalledToTheHuntReplacementEffect extends ReplacementEffectImpl {

    RenataCalledToTheHuntReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control enters the battlefield with an additional +1/+1 counter on it.";
    }

    private RenataCalledToTheHuntReplacementEffect(final RenataCalledToTheHuntReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && creature.isCreature(game)
                && !source.getSourceId().equals(creature.getId())
                && creature.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public RenataCalledToTheHuntReplacementEffect copy() {
        return new RenataCalledToTheHuntReplacementEffect(this);
    }
}
