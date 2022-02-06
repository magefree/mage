
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileFromZoneTargetEffect extends OneShotEffect {

    private final Zone zone;
    private final FilterCard filter;
    private final int amount;
    private final boolean withSource;

    public ExileFromZoneTargetEffect(Zone zone, boolean withSource) {
        this(zone, StaticFilters.FILTER_CARD, withSource);
    }

    public ExileFromZoneTargetEffect(Zone zone, FilterCard filter, boolean withSource) {
        this(zone, filter, 1, withSource);
    }

    public ExileFromZoneTargetEffect(Zone zone, FilterCard filter, int amount, boolean withSource) {
        super(Outcome.Exile);
        this.zone = zone;
        this.filter = filter;
        this.amount = amount;
        this.withSource = withSource;
    }

    public ExileFromZoneTargetEffect(final ExileFromZoneTargetEffect effect) {
        super(effect);
        this.zone = effect.zone;
        this.filter = effect.filter.copy();
        this.amount = effect.amount;
        this.withSource = effect.withSource;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        Target target = null;
        switch (zone) {
            case HAND:
                target = new TargetCardInHand(Math.min(player.getHand().count(filter, game), amount), filter);
                break;
            case GRAVEYARD:
                target = new TargetCardInYourGraveyard(Math.min(player.getGraveyard().count(filter, game), amount), filter);
                break;
            default:
        }
        if (target == null || !target.canChoose(source.getSourceId(), player.getId(), game)) {
            return true;
        }
        target.chooseTarget(Outcome.Exile, player.getId(), source, game);
        Cards cards = new CardsImpl(target.getTargets());
        if (withSource) {
            return player.moveCardsToExile(cards.getCards(game), source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        }
        return player.moveCards(cards, Zone.EXILED, source, game);
    }

    @Override
    public ExileFromZoneTargetEffect copy() {
        return new ExileFromZoneTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "target " + (mode.getTargets().isEmpty() ? "player" : mode.getTargets().get(0).getTargetName())
                + " exiles " + CardUtil.numberToText(amount, "a") + ' ' + filter.getMessage()
                + " from their " + zone.toString().toLowerCase();
    }
}
