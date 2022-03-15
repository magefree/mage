package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * This ability has no effect by default and will always return false on the
 * call to apply. This is because of how the {@link ReboundEffect} works. It
 * will install the effect if and only if the spell was cast from the
 * {@link Zone#HAND Hand}.
 * <p/>
 * 702.85. Rebound
 * <p/>
 * 702.85a Rebound appears on some instants and sorceries. It represents a
 * static ability that functions while the spell is on the stack and may create
 * a delayed triggered ability. "Rebound" means "If this spell was cast from
 * your hand, instead of putting it into your graveyard as it resolves, exile it
 * and, at the beginning of your next upkeep, you may cast this card from exile
 * without paying its mana cost."
 * <p/>
 * 702.85b Casting a card without paying its mana cost as the result of a
 * rebound ability follows the rules for paying alternative costs in rules
 * 601.2b and 601.2e-g.
 * <p/>
 * 702.85c Multiple instances of rebound on the same spell are redundant.
 *
 * @author maurer.it_at_gmail.com, noxx
 */
public class ReboundAbility extends SimpleStaticAbility {

    public ReboundAbility() {
        super(Zone.STACK, new ReboundCastFromHandReplacementEffect());
    }

    public ReboundAbility(final ReboundAbility ability) {
        super(ability);
    }

    @Override
    public ReboundAbility copy() {
        return new ReboundAbility(this);
    }

    @Override
    public String getRule() {
        return "Rebound <i>(If you cast this spell from your hand, "
                + "exile it as it resolves. At the beginning of your next upkeep, "
                + "you may cast this card from exile without paying its mana cost.)</i>";
    }
}

class ReboundCastFromHandReplacementEffect extends ReplacementEffectImpl {

    ReboundCastFromHandReplacementEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
    }

    private ReboundCastFromHandReplacementEffect(final ReboundCastFromHandReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // rules:
        // If a spell with rebound that you cast from your hand doesn’t resolve for any reason (due being countered
        // by a spell like Cancel, or because all of its targets are illegal), rebound has no effect.
        // The spell is simply put into your graveyard. You won’t get to cast it again next turn.
        // (2010-06-15)
        if (((ZoneChangeEvent) event).getFromZone() != Zone.STACK
                || ((ZoneChangeEvent) event).getToZone() != Zone.GRAVEYARD
                || event.getSourceId() == null
                || !event.getSourceId().equals(source.getSourceId())) {
            return false;
        } // if countered the source.sourceId is different or null if it fizzles
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getFromZone() == Zone.HAND;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(source.getSourceId());
        if (sourceSpell != null && sourceSpell.isCopy()) {
            return false;
        }
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Player player = game.getPlayer(sourceCard.getOwnerId());
        if (player == null) {
            return false;
        }
        // Add the delayed triggered effect
        player.moveCardsToExile(sourceCard, source, game, true, null, "Rebound");
        game.addDelayedTriggeredAbility(new ReboundEffectCastFromExileDelayedTrigger(sourceCard, game), source);
        return true;
    }

    @Override
    public ReboundCastFromHandReplacementEffect copy() {
        return new ReboundCastFromHandReplacementEffect(this);
    }

}

class ReboundEffectCastFromExileDelayedTrigger extends DelayedTriggeredAbility {

    ReboundEffectCastFromExileDelayedTrigger(Card card, Game game) {
        super(new ReboundCastSpellFromExileEffect().setTargetPointer(new FixedTarget(card, game)), Duration.Custom, true, true);
    }

    private ReboundEffectCastFromExileDelayedTrigger(final ReboundEffectCastFromExileDelayedTrigger ability) {
        super(ability);
    }

    @Override
    public ReboundEffectCastFromExileDelayedTrigger copy() {
        return new ReboundEffectCastFromExileDelayedTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId());
    }

    @Override
    public String getRule() {
        return "Rebound - You may cast {this} from exile without paying its mana cost.";
    }
}

/**
 * Will be triggered by {@link ReboundEffectCastFromExileDelayedTrigger} and
 * will simply cast the spell then remove it from its former home in exile.
 *
 * @author maurer.it_at_gmail.com
 */
class ReboundCastSpellFromExileEffect extends OneShotEffect {

    ReboundCastSpellFromExileEffect() {
        super(Outcome.PlayForFree);
    }

    private ReboundCastSpellFromExileEffect(final ReboundCastSpellFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card reboundCard = game.getCard(getTargetPointer().getFirst(game, source));
        return player != null && reboundCard != null && CardUtil.castSpellWithAttributesForFree(
                player, source, game, new CardsImpl(reboundCard), StaticFilters.FILTER_CARD
        );
    }

    @Override
    public ReboundCastSpellFromExileEffect copy() {
        return new ReboundCastSpellFromExileEffect(this);
    }
}
