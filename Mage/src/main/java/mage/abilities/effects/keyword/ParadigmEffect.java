package mage.abilities.effects.keyword;

import mage.ApprovingObject;
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
        // blueprint must be independent of the exiled card, which can leave exile without any effect on paradigm
        Card blueprint = spell.getMainCard().copy();
        // copied spells have no card to exile, they just leave the stack
        if (!spell.isCopy()) {
            controller.moveCards(spell.getMainCard(), Zone.EXILED, source, game);
        }
        String resolvedKey = "paradigmResolved|" + controller.getId() + '|' + spell.getName();
        if (game.getState().getValue(resolvedKey) == null) {
            game.getState().setValue(resolvedKey, Boolean.TRUE);
            game.addDelayedTriggeredAbility(new ParadigmDelayedTriggeredAbility(blueprint), source);
        }
        return true;
    }
}

class ParadigmDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ParadigmDelayedTriggeredAbility(Card blueprint) {
        super(new ParadigmCastCopyEffect(blueprint), Duration.EndOfGame, false, false);
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

    private final Card blueprint;

    ParadigmCastCopyEffect(Card blueprint) {
        super(Outcome.PlayForFree);
        this.blueprint = blueprint;
    }

    private ParadigmCastCopyEffect(final ParadigmCastCopyEffect effect) {
        super(effect);
        this.blueprint = effect.blueprint.copy();
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
        if (!controller.chooseUse(outcome, "Cast a copy of " + blueprint.getName() + " without paying its mana cost?", source, game)) {
            return true;
        }
        Card copiedCard = game.copyCard(blueprint, source, controller.getId());
        copiedCard.setZone(Zone.EXILED, game);
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        controller.cast(controller.chooseAbilityForCast(copiedCard, game, true), game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
