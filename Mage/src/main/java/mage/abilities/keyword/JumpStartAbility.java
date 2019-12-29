package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.ContinuousEffect;
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
 * Jump-start is found on instants and sorceries. You can cast a card with
 * jump-start from your graveyard by paying all its regular costs and one
 * additional cost: discarding a card from your hand. Casting a spell with
 * jump-start follows all the normal timing rules, so sorceries with jump-start
 * are still limited to your main phases. A spell with jump-start that was cast
 * from your graveyard can still be countered, and if it has targets, it won't
 * do anything if all its targets disappear or otherwise become illegal. After a
 * spell with jump-start cast from your graveyard resolves, is countered, or
 * leaves the stack in any way, it's exiled.
 *
 * @author TheElk801
 */
public class JumpStartAbility extends SpellAbility {

    private boolean replacementEffectAdded = false;

    public JumpStartAbility(Card card) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with jump-start");
        zone = Zone.GRAVEYARD;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        Cost cost = new DiscardTargetCost(new TargetCardInHand());
        cost.setText("");
        this.addCost(cost);
    }

    public JumpStartAbility(final JumpStartAbility ability) {
        super(ability);
        this.replacementEffectAdded = ability.replacementEffectAdded;
    }

    @Override
    public SpellAbility getSpellAbilityToResolve(Game game) {
        Card card = game.getCard(getSourceId());
        if (card != null) {
            if (!replacementEffectAdded) {
                replacementEffectAdded = true;
                ContinuousEffect effect = new JumpStartReplacementEffect();
                effect.setTargetPointer(new FixedTarget(getSourceId(), game.getState().getZoneChangeCounter(getSourceId())));
                game.addEffect(effect, this);
            }
        }
        return this;
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
            return game.getState().getZoneChangeCounter(source.getSourceId()) == source.getSourceObjectZoneChangeCounter() + 1;

        }
        return false;
    }
}
