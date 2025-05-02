package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.TargetCard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInExile extends TargetCard {

    // If null, can target any card in exile matching [filter]
    // If non-null, can only target
    private final UUID zoneId;

    /**
     * @param filter filter for the card to be a target
     */
    public TargetCardInExile(FilterCard filter) {
        this(1, 1, filter);
    }

    /**
     * @param minNumTargets minimum number of targets
     * @param maxNumTargets maximum number of targets
     * @param filter        filter for the card to be a target
     */
    public TargetCardInExile(int minNumTargets, int maxNumTargets, FilterCard filter) {
        this(minNumTargets, maxNumTargets, filter, null);
    }

    /**
     * @param filter filter for the card to be a target
     * @param zoneId if non-null can only target cards in that exileZone. if null card can be in ever exile zone.
     */
    public TargetCardInExile(FilterCard filter, UUID zoneId) {
        this(1, 1, filter, zoneId);
    }

    /**
     * @param minNumTargets minimum number of targets
     * @param maxNumTargets maximum number of targets
     * @param filter        filter for the card to be a target
     * @param zoneId        if non-null can only target cards in that exileZone. if null card can be in ever exile zone.
     */
    public TargetCardInExile(int minNumTargets, int maxNumTargets, FilterCard filter, UUID zoneId) {
        super(minNumTargets, maxNumTargets, Zone.EXILED, filter);
        this.zoneId = zoneId;
    }

    protected TargetCardInExile(final TargetCardInExile target) {
        super(target);
        this.zoneId = target.zoneId;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        if (zoneId == null) { // no specific exile zone
            for (Card card : game.getExile().getAllCardsByRange(game, sourceControllerId)) {
                if (filter.match(card, sourceControllerId, source, game)) {
                    possibleTargets.add(card.getId());
                }
            }
        } else {
            ExileZone exileZone = game.getExile().getExileZone(zoneId);
            if (exileZone != null) {
                for (Card card : exileZone.getCards(game)) {
                    if (filter.match(card, sourceControllerId, game)) {
                        possibleTargets.add(card.getId());
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        if (zoneId == null) { // no specific exile zone
            int numberTargets = 0;
            for (ExileZone exileZone : game.getExile().getExileZones()) {
                numberTargets += exileZone.count(filter, sourceControllerId, source, game);
                if (numberTargets >= this.minNumberOfTargets) {
                    return true;
                }
            }
        } else {
            ExileZone exileZone = game.getExile().getExileZone(zoneId);
            if (exileZone != null) {
                if (exileZone.count(filter, sourceControllerId, source, game) >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
            if (zoneId == null) { // no specific exile zone
                return filter.match(card, source.getControllerId(), source, game);
            }
            ExileZone exile = game.getExile().getExileZone(zoneId);
            if (exile != null && exile.contains(id)) {
                return filter.match(card, source.getControllerId(), source, game);
            }
        }
        return false;
    }

    @Override
    public TargetCardInExile copy() {
        return new TargetCardInExile(this);
    }
}
