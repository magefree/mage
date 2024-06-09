
package mage.cards.g;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

public final class GlimpseOfNature extends CardImpl {

    public GlimpseOfNature (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");


        // Whenever you cast a creature spell this turn, draw a card.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new GlimpseOfNatureTriggeredAbility()));
    }

    private GlimpseOfNature(final GlimpseOfNature card) {
        super(card);
    }

    @Override
    public GlimpseOfNature copy() {
        return new GlimpseOfNature(this);
    }

}

class GlimpseOfNatureTriggeredAbility extends DelayedTriggeredAbility {

    public GlimpseOfNatureTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false);
    }

    private GlimpseOfNatureTriggeredAbility(final GlimpseOfNatureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && StaticFilters.FILTER_SPELL_A_CREATURE.match(spell, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GlimpseOfNatureTriggeredAbility copy() {
        return new GlimpseOfNatureTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you cast a creature spell this turn, draw a card.";
    }
}