package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;
import mage.ApprovingObject;

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

    public ReboundAbility(ReboundAbility ability) {
        super(ability);
    }

    @Override
    public ReboundAbility copy() {
        return new ReboundAbility(this);
    }
}

class ReboundCastFromHandReplacementEffect extends ReplacementEffectImpl {

    ReboundCastFromHandReplacementEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
        this.staticText = "Rebound <i>(If you cast this spell from your hand, "
                + "exile it as it resolves. At the beginning of your next upkeep, "
                + "you may cast this card from exile without paying its mana cost.)</i>";
    }

    ReboundCastFromHandReplacementEffect(ReboundCastFromHandReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getFromZone() == Zone.STACK
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && event.getSourceId() != null
                && event.getSourceId().equals(source.getSourceId())) { // if countered the source.sourceId is different or null if it fizzles
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && spell.getFromZone() == Zone.HAND) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(source.getSourceId());
        if (sourceSpell != null && sourceSpell.isCopy()) {
            return false;
        } else {
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                Player player = game.getPlayer(sourceCard.getOwnerId());
                if (player != null) {
                    // Add the delayed triggered effect
                    ReboundEffectCastFromExileDelayedTrigger trigger
                            = new ReboundEffectCastFromExileDelayedTrigger(source.getSourceId(), source.getSourceId());
                    game.addDelayedTriggeredAbility(trigger, source);

                    player.moveCardToExileWithInfo(sourceCard, sourceCard.getId(),
                            player.getName() + " Rebound", source.getSourceId(), game, Zone.STACK, true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ReboundCastFromHandReplacementEffect copy() {
        return new ReboundCastFromHandReplacementEffect(this);
    }

}

class ReboundEffectCastFromExileDelayedTrigger extends DelayedTriggeredAbility {

    ReboundEffectCastFromExileDelayedTrigger(UUID cardId, UUID sourceId) {
        super(new ReboundCastSpellFromExileEffect());
        setSourceId(sourceId);
        this.optional = true;
    }

    ReboundEffectCastFromExileDelayedTrigger(ReboundEffectCastFromExileDelayedTrigger ability) {
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
        return MyTurnCondition.instance.apply(game, this);
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

    private static String castFromExileText = "Rebound - You may cast {this} "
            + "from exile without paying its mana cost";

    ReboundCastSpellFromExileEffect() {
        super(Outcome.PlayForFree);
        staticText = castFromExileText;
    }

    ReboundCastSpellFromExileEffect(ReboundCastSpellFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone zone = game.getExile().getExileZone(source.getSourceId());
        if (zone == null
                || zone.isEmpty()) {
            return false;
        }
        Card reboundCard = zone.get(source.getSourceId(), game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null
                && reboundCard != null) {
            game.getState().setValue("PlayFromNotOwnHandZone" + reboundCard.getId(), Boolean.TRUE);
            Boolean cardWasCast = player.cast(player.chooseAbilityForCast(reboundCard, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + reboundCard.getId(), null);
            return cardWasCast;
        }
        return false;
    }

    @Override
    public ReboundCastSpellFromExileEffect copy() {
        return new ReboundCastSpellFromExileEffect(this);
    }

}
