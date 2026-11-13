package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * 702.192a Paradigm represents two spell abilities, one of which creates a
 * delayed triggered ability: "Then exile this spell" and "After you first
 * resolve a spell with this name, you may cast a copy of it from exile without
 * paying its mana cost at the beginning of each of your first main phases."
 * <p>
 * It's a spell effect instead of a static ability because it must only work on
 * resolve (not for countered or fizzled spells) and must also work for copied
 * spells: if a copy is the first spell with this name you resolve, you get the
 * delayed trigger.
 *
 * @author NovaGlow
 */
public class ParadigmEffect extends OneShotEffect {

    private static final String RULE = "Paradigm <i>(Then exile this spell. After you first resolve a spell with " +
            "this name, you may cast a copy of it from exile without paying its mana cost " +
            "at the beginning of each of your first main phases.)</i>";

    public ParadigmEffect() {
        super(Outcome.Benefit);
        staticText = "<br>" + RULE;
    }

    private ParadigmEffect(final ParadigmEffect effect) {
        super(effect);
    }

    @Override
    public ParadigmEffect copy() {
        return new ParadigmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(source.getId());
        if (controller == null || spell == null) {
            return false;
        }
        // copied spells have no card to exile, they just leave the stack
        if (!spell.isCopy()) {
            controller.moveCards(spell.getCard(), Zone.EXILED, source, game);
        }
        String resolvedKey = "paradigmResolved|" + controller.getId() + '|' + spell.getName();
        if (game.getState().getValue(resolvedKey) == null) {
            game.getState().setValue(resolvedKey, Boolean.TRUE);
            // it's a fake spell (template) like in Epic, it doesn't depend on the exiled card
            game.addDelayedTriggeredAbility(new ParadigmDelayedTriggeredAbility(spell.copy()), source);
        }
        return true;
    }
}

class ParadigmDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ParadigmDelayedTriggeredAbility(Spell spell) {
        super(new ParadigmCastCopyEffect(spell), Duration.EndOfGame, false, false);
    }

    private ParadigmDelayedTriggeredAbility(final ParadigmDelayedTriggeredAbility ability) {
        super(ability);
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
        return "Paradigm &mdash; At the beginning of your first main phase, " +
                "you may cast a copy of this spell from exile without paying its mana cost.";
    }
}

class ParadigmCastCopyEffect extends OneShotEffect {

    private final Spell spell;

    ParadigmCastCopyEffect(Spell spell) {
        super(Outcome.PlayForFree);
        this.spell = spell;
    }

    private ParadigmCastCopyEffect(final ParadigmCastCopyEffect effect) {
        super(effect);
        this.spell = effect.spell;
    }

    @Override
    public ParadigmCastCopyEffect copy() {
        return new ParadigmCastCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // a declined copy would cease to exist right away, so there is no need to create it
        if (!controller.chooseUse(outcome, "Cast a copy of " + spell.getName() + " without paying its mana cost?", source, game)) {
            return true;
        }
        Card copiedCard = game.copyCard(spell.getMainCard(), source, controller.getId());
        copiedCard.setZone(Zone.EXILED, game);
        CardUtil.castSingle(controller, source, game, copiedCard, true, null);
        return true;
    }
}
