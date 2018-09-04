package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class JumpStartAbility extends SpellAbility {

    public JumpStartAbility(Card card) {
        super(card.getManaCost(), card.getName() + " with jump-start", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE);
        this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
        Cost cost = new DiscardTargetCost(new TargetCardInHand());
        cost.setText("");
        this.addCost(cost);
        this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
        this.addEffect(new JumpStartReplacementEffect());
        this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = card.getSpellAbility().getTiming();

    }

    public JumpStartAbility(final JumpStartAbility ability) {
        super(ability);
    }

    @Override
    public JumpStartAbility copy() {
        return new JumpStartAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Jump-start <i>(You may cast this card from your graveyard "
                + "by discarding a card in addition to paying its other costs. "
                + "Then exile this card.)</i>";
    }
}

class JumpStartReplacementEffect extends ReplacementEffectImpl {

    public JumpStartReplacementEffect() {
        super(Duration.OneUse, Outcome.Exile);
        staticText = "(If this spell was cast with jump-start, "
                + "exile it instead of putting it anywhere else "
                + "any time it would leave the stack)";
    }

    public JumpStartReplacementEffect(final JumpStartReplacementEffect effect) {
        super(effect);
    }

    @Override
    public JumpStartReplacementEffect copy() {
        return new JumpStartReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                discard();
                return controller.moveCards(
                        card, Zone.EXILED, source, game, false, false, false, event.getAppliedEffects());
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.STACK
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED) {

            int zcc = game.getState().getZoneChangeCounter(source.getSourceId());
            if (((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == zcc) {
                return true;
            }

        }
        return false;
    }
}
