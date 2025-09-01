package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
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
    private final boolean onlyYou;
    private final boolean tokens;

    public GraveyardFromAnywhereExileReplacementEffect(FilterCard filter, boolean onlyYou) {
        this(Duration.WhileOnBattlefield, filter, onlyYou, false);
    }

    public GraveyardFromAnywhereExileReplacementEffect(boolean onlyYou, boolean tokens) {
        this(Duration.WhileOnBattlefield, StaticFilters.FILTER_CARD_A, onlyYou, tokens);
    }

    public GraveyardFromAnywhereExileReplacementEffect(Duration duration) {
        this(duration, StaticFilters.FILTER_CARD_A, true, false);
    }

    protected GraveyardFromAnywhereExileReplacementEffect(Duration duration, FilterCard filter, boolean onlyYou, boolean tokens) {
        super(duration, Outcome.Exile);
        this.filter = filter;
        this.onlyYou = onlyYou;
        this.tokens = tokens;
        this.setText();
    }

    protected GraveyardFromAnywhereExileReplacementEffect(final GraveyardFromAnywhereExileReplacementEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.onlyYou = effect.onlyYou;
        this.tokens = effect.tokens;
    }

    private void setText() {
        this.staticText = "If " + CardUtil.addArticle(filter.getMessage()) + (tokens ? " or token" : "")
                + " would be put into " + (onlyYou ? "your" : "a") + " graveyard from anywhere"
                + (duration == Duration.EndOfTurn ? " this turn" : "") + ", exile "
                + ((filter == StaticFilters.FILTER_CARD_A && !tokens) ? "that card" : "it") + " instead";
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
        if (card != null && (!onlyYou || card.isOwnedBy(source.getControllerId())) && (filter == null || filter.match(card, game))) {
            return true;
        }
        Permanent token = game.getPermanent(event.getTargetId());
        if (tokens && (token instanceof PermanentToken && (!onlyYou || token.isOwnedBy(source.getControllerId())))) {
            return true;
        }
        return false;
    }
}
