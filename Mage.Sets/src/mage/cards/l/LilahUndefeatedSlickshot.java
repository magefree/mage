package mage.cards.l;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LilahUndefeatedSlickshot extends CardImpl {

    public LilahUndefeatedSlickshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever you cast a multicolored instant or sorcery spell from your hand, exile that spell instead of putting it into your graveyard as it resolves. If you do, it becomes plotted.
        this.addAbility(new LilahUndefeatedSlickshotTriggeredAbility());
    }

    private LilahUndefeatedSlickshot(final LilahUndefeatedSlickshot card) {
        super(card);
    }

    @Override
    public LilahUndefeatedSlickshot copy() {
        return new LilahUndefeatedSlickshot(this);
    }
}

class LilahUndefeatedSlickshotTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell();

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    LilahUndefeatedSlickshotTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private LilahUndefeatedSlickshotTriggeredAbility(final LilahUndefeatedSlickshotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LilahUndefeatedSlickshotTriggeredAbility copy() {
        return new LilahUndefeatedSlickshotTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !filter.match(spell, getControllerId(), this, game) || !Zone.HAND.equals(spell.getFromZone())) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new LilahUndefeatedSlickshotEffect(new MageObjectReference(spell, game)));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a multicolored instant or sorcery spell from your hand, "
                + "exile that spell instead of putting it into your graveyard as it resolves. "
                + "If you do, it becomes plotted.";
    }
}

/**
 * Quite inspired by {@link mage.cards.f.FeatherTheRedeemed Feather the Redeemed}
 */
class LilahUndefeatedSlickshotEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    LilahUndefeatedSlickshotEffect(MageObjectReference mor) {
        super(Duration.WhileOnStack, Outcome.Benefit);
        this.mor = mor;
    }

    private LilahUndefeatedSlickshotEffect(final LilahUndefeatedSlickshotEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public LilahUndefeatedSlickshotEffect copy() {
        return new LilahUndefeatedSlickshotEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(event.getTargetId());
        if (sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        PlotAbility.doExileAndPlotCard(sourceSpell, game, source);
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
                || mor.getZoneChangeCounter() != game.getState().getZoneChangeCounter(event.getSourceId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(mor.getSourceId());
        return spell != null && spell.isInstantOrSorcery(game);
    }
}