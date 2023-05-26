package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FblthpTheLost extends CardImpl {

    public FblthpTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Fblthp, the Lost enters the battlefield, draw a card. If it entered from your library or was cast from your library, draw two cards instead.
        this.addAbility(new FblthpTheLostTriggeredAbility());

        // When Fblthp becomes the target of a spell, shuffle Fblthp into its owner's library.
        this.addAbility(new FblthpTheLostTargetedTriggeredAbility());
    }

    private FblthpTheLost(final FblthpTheLost card) {
        super(card);
    }

    @Override
    public FblthpTheLost copy() {
        return new FblthpTheLost(this);
    }
}

class FblthpTheLostTriggeredAbility extends EntersBattlefieldTriggeredAbility {
    FblthpTheLostTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1));
        this.addWatcher(new FblthpTheLostWatcher());
    }

    private FblthpTheLostTriggeredAbility(final FblthpTheLostTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FblthpTheLostTriggeredAbility copy() {
        return new FblthpTheLostTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        EntersTheBattlefieldEvent entersEvent = (EntersTheBattlefieldEvent) event;
        if (entersEvent.getFromZone() == Zone.LIBRARY) {
            this.getEffects().clear();
            this.addEffect(new DrawCardSourceControllerEffect(2));
            return true;
        }
        FblthpTheLostWatcher watcher = game.getState().getWatcher(FblthpTheLostWatcher.class);
        int zcc = entersEvent.getTarget().getZoneChangeCounter(game) - 1;
        MageObjectReference mor = new MageObjectReference(entersEvent.getTargetId(), zcc, game);
        if (watcher != null && watcher.spellWasCastFromLibrary(mor)) {
            this.getEffects().clear();
            this.addEffect(new DrawCardSourceControllerEffect(2));
            return true;
        }
        this.getEffects().clear();
        this.addEffect(new DrawCardSourceControllerEffect(1));
        return true;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield, draw a card. " +
                "If it entered from your library or was cast from your library, draw two cards instead.";
    }
}

class FblthpTheLostWatcher extends Watcher {

    private final Set<MageObjectReference> spellsCastFromLibrary = new HashSet<>();

    FblthpTheLostWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.LIBRARY) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                spellsCastFromLibrary.add(new MageObjectReference(spell, game));
            }

        }
    }

    boolean spellWasCastFromLibrary(MageObjectReference mor) {
        return spellsCastFromLibrary.contains(mor);

    }

    @Override
    public void reset() {
        super.reset();
        spellsCastFromLibrary.clear();
    }

}

class FblthpTheLostTargetedTriggeredAbility extends TriggeredAbilityImpl {

    FblthpTheLostTargetedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ShuffleIntoLibrarySourceEffect(), false);
    }

    private FblthpTheLostTargetedTriggeredAbility(final FblthpTheLostTargetedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FblthpTheLostTargetedTriggeredAbility copy() {
        return new FblthpTheLostTargetedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject eventSourceObject = game.getObject(event.getSourceId());
        if (event.getTargetId().equals(this.getSourceId()) && eventSourceObject instanceof Spell) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} becomes the target of a spell, shuffle {this} into its owner's library.";
    }

}
