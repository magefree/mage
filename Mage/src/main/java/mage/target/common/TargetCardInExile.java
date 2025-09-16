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

    private final UUID zoneId; // use null to target any exile zone or only specific

    public TargetCardInExile(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInExile(int minNumTargets, int maxNumTargets, FilterCard filter) {
        this(minNumTargets, maxNumTargets, filter, null);
    }

    /**
     * @param zoneId if non-null can only target cards in that exileZone. if null card can be in ever exile zone.
     */
    public TargetCardInExile(FilterCard filter, UUID zoneId) {
        this(1, 1, filter, zoneId);
    }

    /**
     * @param zoneId if non-null can only target cards in that exileZone. if null card can be in ever exile zone.
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
            for (Card card : game.getExile().getCardsInRange(game, sourceControllerId)) {
                if (filter.match(card, sourceControllerId, source, game)) {
                    possibleTargets.add(card.getId());
                }
            }
        } else {
            ExileZone exileZone = game.getExile().getExileZone(zoneId);
            if (exileZone != null) {
                for (Card card : exileZone.getCards(game)) {
                    if (filter.match(card, sourceControllerId, source, game)) {
                        possibleTargets.add(card.getId());
                    }
                }
            }
        }

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public TargetCardInExile copy() {
        return new TargetCardInExile(this);
    }
}
