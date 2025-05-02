package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GandalfOfTheSecretFire extends CardImpl {

    public GandalfOfTheSecretFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell from your hand during an opponent's turn, exile that card with three time counters on it instead of putting it into your graveyard as it resolves. Then if the exiled card doesn't have suspend, it gains suspend.
        this.addAbility(new GandalfOfTheSecretFireTriggeredAbility());
    }

    private GandalfOfTheSecretFire(final GandalfOfTheSecretFire card) {
        super(card);
    }

    @Override
    public GandalfOfTheSecretFire copy() {
        return new GandalfOfTheSecretFire(this);
    }
}

class GandalfOfTheSecretFireTriggeredAbility extends TriggeredAbilityImpl {

    GandalfOfTheSecretFireTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private GandalfOfTheSecretFireTriggeredAbility(final GandalfOfTheSecretFireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GandalfOfTheSecretFireTriggeredAbility copy() {
        return new GandalfOfTheSecretFireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId()) || getControllerId().equals(game.getActivePlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game) || !Zone.HAND.equals(spell.getFromZone())) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new GandalfOfTheSecretFireEffect(spell, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell from your hand during an opponent's turn, exile that " +
                "card with three time counters on it instead of putting it into your graveyard as it resolves. " +
                "Then if the exiled card doesn't have suspend, it gains suspend.";
    }
}

class GandalfOfTheSecretFireEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    GandalfOfTheSecretFireEffect(Spell spell, Game game) {
        super(Duration.OneUse, Outcome.Benefit);
        this.mor = new MageObjectReference(spell.getCard(), game);
    }

    private GandalfOfTheSecretFireEffect(final GandalfOfTheSecretFireEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public GandalfOfTheSecretFireEffect copy() {
        return new GandalfOfTheSecretFireEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell sourceSpell = game.getStack().getSpell(event.getTargetId());
        if (controller == null || sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
        if (controller.moveCardsToExile(sourceSpell, source, game, true, exileId, "Suspended cards of " + controller.getName())) {
            sourceSpell.addCounters(CounterType.TIME.createInstance(3), controller.getId(), source, game);
            game.informPlayers(controller.getLogName() + " exiles " + sourceSpell.getLogName() + " with 3 time counters on it");
        }
        if (!sourceSpell.getAbilities(game).containsClass(SuspendAbility.class)) {
            game.addEffect(new GainSuspendEffect(new MageObjectReference(sourceSpell.getMainCard(), game)), source);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        if (zEvent.getFromZone() != Zone.STACK
                || zEvent.getToZone() != Zone.GRAVEYARD
                || event.getSourceId() == null
                || !event.getSourceId().equals(event.getTargetId())
                || !mor.equals(new MageObjectReference(event.getTargetId(), game))) {
            return false;
        }
        Spell spell = game.getStack().getSpell(mor.getSourceId());
        return spell != null && spell.isInstantOrSorcery(game);
    }
}
