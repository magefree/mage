package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
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

    // we store both Spell and Card to work properly on split cards.
    private final MageObjectReference morSpell;
    private final MageObjectReference morCard;

    GandalfOfTheSecretFireEffect(Spell spell, Game game) {
        super(Duration.OneUse, Outcome.Benefit);
        this.morSpell = new MageObjectReference(spell.getCard(), game);
        this.morCard = new MageObjectReference(spell.getMainCard(), game);
    }

    private GandalfOfTheSecretFireEffect(final GandalfOfTheSecretFireEffect effect) {
        super(effect);
        this.morSpell = effect.morSpell;
        this.morCard = effect.morCard;
    }

    @Override
    public GandalfOfTheSecretFireEffect copy() {
        return new GandalfOfTheSecretFireEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell sourceSpell = morSpell.getSpell(game);
        if (controller == null || sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        controller.moveCards(sourceSpell, Zone.EXILED, source, game);
        SuspendAbility.addTimeCountersAndSuspend(sourceSpell.getMainCard(), 3, source, game);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        return Zone.STACK.equals(zEvent.getFromZone())
                && Zone.GRAVEYARD.equals(zEvent.getToZone())
                && morSpell.refersTo(event.getSourceId(), game) // this is how we check that the spell resolved properly (and was not countered or the like)
                && morCard.refersTo(event.getTargetId(), game); // this is how we check that the card being moved is the one we want.
    }
}
