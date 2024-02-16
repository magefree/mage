package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
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
 * @author Susucr
 */
public final class ArwenWeaverOfHope extends CardImpl {

    public ArwenWeaverOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Each other creature you control enters the battlefield with a number of additional +1/+1 counters on it equal to Arwen, Weaver of Hope's toughness.
        this.addAbility(new SimpleStaticAbility(new ArwenWeaverOfHopeEffect()));
    }

    private ArwenWeaverOfHope(final ArwenWeaverOfHope card) {
        super(card);
    }

    @Override
    public ArwenWeaverOfHope copy() {
        return new ArwenWeaverOfHope(this);
    }
}

class ArwenWeaverOfHopeEffect extends ReplacementEffectImpl {

    ArwenWeaverOfHopeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control enters the battlefield with a number of additional +1/+1 counters on it equal to {this}'s toughness";
    }

    private ArwenWeaverOfHopeEffect(ArwenWeaverOfHopeEffect effect) {
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
            int toughness = sourceCreature.getToughness().getValue();
            if (toughness > 0) {
                creature.addCounters(CounterType.P1P1.createInstance(toughness), source.getControllerId(), source, game);
            }
        }
        return false;
    }

    @Override
    public ArwenWeaverOfHopeEffect copy() {
        return new ArwenWeaverOfHopeEffect(this);
    }
}
