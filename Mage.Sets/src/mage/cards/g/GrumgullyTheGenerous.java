package mage.cards.g;

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
 * @author TheElk801
 */
public final class GrumgullyTheGenerous extends CardImpl {

    public GrumgullyTheGenerous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each other non-Human creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new GrumgullyTheGenerousReplacementEffect()));
    }

    private GrumgullyTheGenerous(final GrumgullyTheGenerous card) {
        super(card);
    }

    @Override
    public GrumgullyTheGenerous copy() {
        return new GrumgullyTheGenerous(this);
    }
}

class GrumgullyTheGenerousReplacementEffect extends ReplacementEffectImpl {

    GrumgullyTheGenerousReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other non-Human creature you control " +
                "enters the battlefield with an additional +1/+1 counter on it.";
    }

    private GrumgullyTheGenerousReplacementEffect(final GrumgullyTheGenerousReplacementEffect effect) {
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
                && creature.isControlledBy(source.getControllerId())
                && !creature.hasSubtype(SubType.HUMAN, game);
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
    public GrumgullyTheGenerousReplacementEffect copy() {
        return new GrumgullyTheGenerousReplacementEffect(this);
    }
}
