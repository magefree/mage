package mage.target.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public class TargetCardInYourGraveyardOrExile extends TargetCard {

    private final FilterCard filterGraveyard;
    private final FilterCard filterExile;

    public TargetCardInYourGraveyardOrExile(FilterCard filterGraveyard, FilterCard filterExile) {
        super(1, 1, Zone.GRAVEYARD, filterGraveyard); // we do not actually use those, as all is overwritten
        this.filterGraveyard = filterGraveyard;
        this.filterExile = filterExile;
        this.targetName = filterGraveyard.getMessage() + " or " + filterExile.getMessage();
    }

    protected TargetCardInYourGraveyardOrExile(final TargetCardInYourGraveyardOrExile target) {
        super(target);
        this.filterGraveyard = target.filterGraveyard;
        this.filterExile = target.filterExile;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        Player player = game.getPlayer(sourceControllerId);
        if (player == null) {
            return possibleTargets;
        }

        // in your graveyard
        possibleTargets.addAll(getAllPossibleTargetInGraveyard(game, player, sourceControllerId, source, filterGraveyard, isNotTarget()));
        // in exile
        possibleTargets.addAll(getAllPossibleTargetInExile(game, player, sourceControllerId, source, filterExile, isNotTarget()));

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return this.canTarget(source == null ? null : source.getControllerId(), id, source, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        switch (game.getState().getZone(id)) {
            case GRAVEYARD:
                return playerId == null ? filterGraveyard.match(card, game) : filterGraveyard.match(card, playerId, source, game);
            case EXILED:
                return playerId == null ? filterExile.match(card, game) : filterExile.match(card, playerId, source, game);
            default:
                return false;
        }
    }

    @Override
    public TargetCardInYourGraveyardOrExile copy() {
        return new TargetCardInYourGraveyardOrExile(this);
    }
}
