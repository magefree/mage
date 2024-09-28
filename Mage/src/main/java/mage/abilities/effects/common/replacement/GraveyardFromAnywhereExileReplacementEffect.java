package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.util.CardUtil;

/**
 * @author notgreat
 */
public class GraveyardFromAnywhereExileReplacementEffect extends ReplacementEffectImpl {

    private final FilterCard filter;
    private final boolean only_you;
    private final boolean tokens;

    public GraveyardFromAnywhereExileReplacementEffect(FilterCard filter, boolean only_you) {
        this(Duration.WhileOnBattlefield, filter, only_you, false);
    }

    public GraveyardFromAnywhereExileReplacementEffect(boolean only_you, boolean tokens) {
        this(Duration.WhileOnBattlefield, null, only_you, tokens);
    }

    public GraveyardFromAnywhereExileReplacementEffect(Duration duration, FilterCard filter, boolean only_you, boolean tokens) {
        super(duration, Outcome.Exile);
        this.filter = filter;
        this.only_you = only_you;
        this.tokens = tokens;
        this.set_text();
    }

    private GraveyardFromAnywhereExileReplacementEffect(final GraveyardFromAnywhereExileReplacementEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.only_you = effect.only_you;
        this.tokens = effect.tokens;
    }

    private void set_text() {
        this.staticText = "If " + (filter != null ? CardUtil.addArticle(filter.getMessage()) : "a card") + (tokens ? " or token" : "")
                + " would be put into " + (only_you ? "your" : "a") + " graveyard from anywhere"
                + (duration == Duration.EndOfTurn ? " this turn" : "") + ", exile "
                + ((filter == null && !tokens) ? "that card" : "it") + " instead";
    }

    @Override
    public GraveyardFromAnywhereExileReplacementEffect copy() {
        return new GraveyardFromAnywhereExileReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        if (card != null && (!only_you || card.isOwnedBy(source.getControllerId())) && (filter == null || filter.match(card, game))) {
            return true;
        }
        Permanent token = game.getPermanent(event.getTargetId());
        if (tokens && (token instanceof PermanentToken && (!only_you || token.isOwnedBy(source.getControllerId())))) {
            return true;
        }
        return false;
    }
}
