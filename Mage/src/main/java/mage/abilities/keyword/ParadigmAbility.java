package mage.abilities.keyword;

import mage.ApprovingObject;
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
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ParadigmAbility extends SimpleStaticAbility {

    public ParadigmAbility() {
        super(Zone.STACK, new ParadigmReplacementEffect());
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

class ParadigmUtil {

    private static final String KEY_PREFIX = "ParadigmFirstResolved";
    private static final String FIRST_SOURCE_KEY_PREFIX = "ParadigmFirstResolvedSource";

    static String getKey(UUID controllerId, String cardName) {
        return KEY_PREFIX + controllerId + cardName;
    }

    static String getFirstSourceKey(UUID controllerId, String cardName) {
        return FIRST_SOURCE_KEY_PREFIX + controllerId + cardName;
    }
}

class ParadigmReplacementEffect extends ReplacementEffectImpl {

    ParadigmReplacementEffect() {
        super(Duration.WhileOnStack, Outcome.Exile);
    }

    private ParadigmReplacementEffect(final ParadigmReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ParadigmReplacementEffect copy() {
        return new ParadigmReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getFromZone() != Zone.STACK
                || ((ZoneChangeEvent) event).getToZone() != Zone.GRAVEYARD
                || event.getSourceId() == null
                || !event.getSourceId().equals(source.getSourceId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (spell == null || sourceCard == null) {
            return false;
        }
        String key = ParadigmUtil.getKey(source.getControllerId(), sourceCard.getName());
        if (!Boolean.TRUE.equals(game.getState().getValue(key))) {
            return true;
        }
        return source.getSourceId().equals(game.getState().getValue(
                ParadigmUtil.getFirstSourceKey(source.getControllerId(), sourceCard.getName())
        ));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card sourceCard = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceCard == null || controller == null) {
            return false;
        }
        controller.moveCardsToExile(sourceCard, source, game, true, null, "Paradigm");
        String key = ParadigmUtil.getKey(source.getControllerId(), sourceCard.getName());
        if (!Boolean.TRUE.equals(game.getState().getValue(key))) {
            game.getState().setValue(key, Boolean.TRUE);
            game.getState().setValue(
                    ParadigmUtil.getFirstSourceKey(source.getControllerId(), sourceCard.getName()),
                    sourceCard.getId()
            );
            game.addDelayedTriggeredAbility(new ParadigmDelayedTriggeredAbility(sourceCard, game), source);
        }
        return true;
    }
}

class ParadigmDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID cardId;

    ParadigmDelayedTriggeredAbility(Card card, Game game) {
        super(new ParadigmCastCopyEffect(card.getId()), Duration.Custom, false, true);
        this.cardId = card.getId();
    }

    private ParadigmDelayedTriggeredAbility(final ParadigmDelayedTriggeredAbility ability) {
        super(ability);
        this.cardId = ability.cardId;
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
        Card card = game.getCard(cardId);
        return game.isActivePlayer(getControllerId())
                && card != null;
    }

    @Override
    public String getRule() {
        return "Paradigm - You may cast a copy of {this} from exile without paying its mana cost.";
    }
}

class ParadigmCastCopyEffect extends OneShotEffect {

    private static final Logger logger = Logger.getLogger(ParadigmCastCopyEffect.class);
    private final UUID cardId;

    ParadigmCastCopyEffect(UUID cardId) {
        super(Outcome.PlayForFree);
        this.cardId = cardId;
    }

    private ParadigmCastCopyEffect(final ParadigmCastCopyEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public ParadigmCastCopyEffect copy() {
        return new ParadigmCastCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(cardId);
        if (controller == null || card == null) {
            return false;
        }
        Card copiedCard = game.copyCard(card, source, source.getControllerId());
        if (copiedCard == null) {
            return false;
        }
        if (copiedCard.getSpellAbility() == null) {
            logger.error("Paradigm: spell ability == null " + copiedCard.getName());
            return false;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        controller.cast(controller.chooseAbilityForCast(copiedCard, game, true), game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
