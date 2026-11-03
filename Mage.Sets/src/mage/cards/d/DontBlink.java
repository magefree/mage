package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastFromExileWatcher;

import java.util.UUID;

public final class DontBlink extends CardImpl {

    public DontBlink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Until end of turn, if one or more creatures would enter the battlefield from exile or after being cast from exile, their owners shuffle them into their libraries instead.
        this.getSpellAbility().addEffect(new DontBlinkReplacementEffect());
        this.getSpellAbility().addWatcher(new CastFromExileWatcher());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private DontBlink(final DontBlink card) {
        super(card);
    }

    @Override
    public DontBlink copy() {
        return new DontBlink(this);
    }
}

class DontBlinkReplacementEffect extends ReplacementEffectImpl {

    DontBlinkReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "until end of turn, if one or more creatures would enter the battlefield from exile or after being cast from exile, their owners shuffle them into their libraries instead";
    }

    private DontBlinkReplacementEffect(final DontBlinkReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DontBlinkReplacementEffect copy() {
        return new DontBlinkReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return new ShuffleIntoLibraryTargetEffect().setTargetPointer(new FixedTarget(((EntersTheBattlefieldEvent)event).getTarget(), game)).apply(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof EntersTheBattlefieldEvent) {
            final EntersTheBattlefieldEvent entersEvent = (EntersTheBattlefieldEvent) event;
            final Permanent permanent = entersEvent.getTarget();
            if (permanent != null && permanent.isCreature(game)) {
                if (entersEvent.getFromZone() == Zone.EXILED) {
                    return true;
                }
                final CastFromExileWatcher watcher = game.getState().getWatcher(CastFromExileWatcher.class);
                return watcher != null && watcher.spellWasCastFromZone(entersEvent.getSourceId(), game.getState().getZoneChangeCounter(entersEvent.getSourceId()));
            }
        }
        return false;
    }
}
