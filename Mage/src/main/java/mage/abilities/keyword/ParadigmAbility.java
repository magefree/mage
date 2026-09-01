package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * Paradigm (SOS)
 * <p>
 * "Paradigm" means "Then exile this spell. After you first resolve a spell
 * with this name, you may cast a copy of it from exile without paying its mana
 * cost at the beginning of each of your first main phases."
 * <p>
 * Modeled on {@link ReboundAbility}: a static ability that functions on the
 * stack and replaces the resolving spell's move to the graveyard with exile,
 * then sets up a delayed triggered ability. Differences from rebound, per the
 * official rulings:
 * <ul>
 * <li>No cast-from-hand restriction.</li>
 * <li>The delayed triggered ability triggers at the beginning of each of the
 * controller's first main phases FOR THE REST OF THE GAME, not once.</li>
 * <li>The trigger creates and offers to cast a COPY of the card — the exiled
 * card itself never leaves exile, and once the ability has been set up it does
 * not matter what happens to the exiled card ("Even if the card leaves exile,
 * the delayed triggered ability will still trigger ... and the copy will still
 * be created").</li>
 * <li>"After you first resolve a spell with this name": only the first
 * resolution sets up the recurring ability — one recurring trigger per card
 * name per player, never one per resolution.</li>
 * </ul>
 * A resolving copy of a spell with paradigm is not exiled (it ceases to exist
 * as it leaves the stack) and does not set up the ability, mirroring how
 * rebound handles copies.
 *
 * @author TheElk801
 */
public class ParadigmAbility extends SimpleStaticAbility {

    public ParadigmAbility() {
        super(Zone.STACK, new ParadigmExileAndSetupEffect());
    }

    protected ParadigmAbility(final ParadigmAbility ability) {
        super(ability);
    }

    @Override
    public ParadigmAbility copy() {
        return new ParadigmAbility(this);
    }

    @Override
    public String getRule() {
        return "Paradigm <i> (Then exile this spell. After you first resolve a spell with this name, " +
                "you may cast a copy of it from exile without paying its mana cost " +
                "at the beginning of each of your first main phases.)</i>";
    }
}

class ParadigmExileAndSetupEffect extends ReplacementEffectImpl {

    ParadigmExileAndSetupEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
    }

    private ParadigmExileAndSetupEffect(final ParadigmExileAndSetupEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Only the resolving spell's own move from the stack to the graveyard is
        // replaced; a countered or fizzled spell carries a different source id,
        // so it is simply put into the graveyard (same handling as rebound).
        return ((ZoneChangeEvent) event).getFromZone() == Zone.STACK
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && event.getSourceId() != null
                && event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(source.getSourceId());
        if (sourceSpell == null || sourceSpell.isCopy()) {
            // a resolving copy ceases to exist instead of being exiled
            return false;
        }
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Player controller = game.getPlayer(sourceSpell.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.moveCardsToExile(sourceCard, source, game, true, null, "Paradigm");
        // "After you first resolve a spell with this name": only the first
        // resolution of this name by this player sets up the recurring ability.
        String setupKey = "paradigmSetup_" + controller.getId() + '_' + sourceCard.getName();
        if (game.getState().getValue(setupKey) == null) {
            game.getState().setValue(setupKey, true);
            game.addDelayedTriggeredAbility(new ParadigmDelayedTriggeredAbility(sourceCard, game), source);
        }
        return true;
    }

    @Override
    public ParadigmExileAndSetupEffect copy() {
        return new ParadigmExileAndSetupEffect(this);
    }
}

class ParadigmDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final String cardName;

    ParadigmDelayedTriggeredAbility(Card card, Game game) {
        super(new ParadigmCastCopyEffect().setTargetPointer(new FixedTarget(card, game)), Duration.EndOfGame, false, false);
        this.cardName = card.getName();
    }

    private ParadigmDelayedTriggeredAbility(final ParadigmDelayedTriggeredAbility ability) {
        super(ability);
        this.cardName = ability.cardName;
    }

    @Override
    public ParadigmDelayedTriggeredAbility copy() {
        return new ParadigmDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId());
    }

    @Override
    public String getRule() {
        return "Paradigm - At the beginning of each of your first main phases, you may cast a copy of "
                + cardName + " without paying its mana cost.";
    }
}

class ParadigmCastCopyEffect extends OneShotEffect {

    ParadigmCastCopyEffect() {
        super(Outcome.PlayForFree);
    }

    private ParadigmCastCopyEffect(final ParadigmCastCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        // The copy is created no matter where the card is now ("Even if the card
        // leaves exile ... the copy will still be created"). Casting it is
        // optional; a copy that isn't cast ceases to exist at the next state
        // check.
        Card copiedCard = game.copyCard(card, source, controller.getId());
        return CardUtil.castSpellWithAttributesForFree(controller, source, game, copiedCard);
    }

    @Override
    public ParadigmCastCopyEffect copy() {
        return new ParadigmCastCopyEffect(this);
    }
}
