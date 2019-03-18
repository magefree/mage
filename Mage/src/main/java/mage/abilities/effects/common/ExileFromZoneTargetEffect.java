
package mage.abilities.effects.common;

import java.util.Locale;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileFromZoneTargetEffect extends OneShotEffect {

    private Zone zone;
    private FilterCard filter;
    private UUID exileId;
    private String exileName;
    private int amount;

    public ExileFromZoneTargetEffect(Zone zone, UUID exileId, String exileName, FilterCard filter) {
        this(zone, exileId, exileName, filter, 1);
    }

    public ExileFromZoneTargetEffect(Zone zone, UUID exileId, String exileName, FilterCard filter, int amount) {
        super(Outcome.Exile);
        this.zone = zone;
        this.filter = filter;
        this.exileId = exileId;
        this.exileName = exileName;
        this.amount = amount;
        setText();
    }

    public ExileFromZoneTargetEffect(final ExileFromZoneTargetEffect effect) {
        super(effect);
        this.zone = effect.zone;
        this.filter = effect.filter.copy();
        this.exileId = effect.exileId;
        this.exileName = effect.exileName;
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
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
            if (target != null && target.canChoose(source.getSourceId(), player.getId(), game)) {
                if (target.chooseTarget(Outcome.Exile, player.getId(), source, game)) {
                    player.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game), source, game, true, exileId, exileName);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ExileFromZoneTargetEffect copy() {
        return new ExileFromZoneTargetEffect(this);
    }

    private void setText() {
        staticText = "target player exiles " + CardUtil.numberToText(amount, "a") + ' ' + filter.getMessage() + " from their " + zone.toString().toLowerCase(Locale.ENGLISH);
    }
}
