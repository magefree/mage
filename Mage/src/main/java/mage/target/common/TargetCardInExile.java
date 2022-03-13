
package mage.target.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.TargetCard;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInExile extends TargetCard {

    private final UUID zoneId;
    private final boolean allExileZones;

    public TargetCardInExile(FilterCard filter) {
        this(1, 1, filter, null);
    }

    /**
     *
     * @param filter
     * @param zoneId - if null card can be in ever exile zone
     */
    public TargetCardInExile(FilterCard filter, UUID zoneId) {
        this(1, 1, filter, zoneId);
    }

    public TargetCardInExile(int minNumTargets, int maxNumTargets, FilterCard filter, UUID zoneId) {
        this(minNumTargets, maxNumTargets, filter, zoneId, false);
    }

    public TargetCardInExile(int minNumTargets, int maxNumTargets, FilterCard filter, UUID zoneId, boolean allExileZones) {
        super(minNumTargets, maxNumTargets, Zone.EXILED, filter);
        this.zoneId = zoneId;
        this.allExileZones = zoneId == null || allExileZones;
    }

    public TargetCardInExile(final TargetCardInExile target) {
        super(target);
        this.zoneId = target.zoneId;
        this.allExileZones = target.allExileZones;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        if (allExileZones) {
            for (Card card : game.getExile().getAllCards(game)) {
                if (filter.match(card, sourceControllerId, game)) {
                    possibleTargets.add(card.getId());
                }
            }
        } else {
            ExileZone exileZone = game.getExile().getExileZone(zoneId);
            if (exileZone != null) {
                for(Card card : exileZone.getCards(game)) {
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
        if (allExileZones) {
            int numberTargets = 0;
            for(ExileZone exileZone : game.getExile().getExileZones()) {
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
            if (allExileZones) {
                return filter.match(card, source.getControllerId(), game);
            }
            ExileZone exile;
            if (zoneId != null) {
                exile = game.getExile().getExileZone(zoneId);
            } else {
                exile = game.getExile().getPermanentExile();
            }
            if (exile != null && exile.contains(id)) {
                return filter.match(card, source.getControllerId(), game);
            }
        }
        return false;
    }

    @Override
    public TargetCardInExile copy() {
        return new TargetCardInExile(this);
    }
}
