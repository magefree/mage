package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Can be used with SearchLibrary only. User hasn't access to libs.
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInLibrary extends TargetCard {

    private int librarySearchLimit;

    public TargetCardInLibrary() {
        this(1, 1, StaticFilters.FILTER_CARD);
    }

    public TargetCardInLibrary(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInLibrary(int numTargets, FilterCard filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetCardInLibrary(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Zone.LIBRARY, filter);
        // 701.15b If a player is searching a hidden zone for cards with a stated quality, such as a card
        // with a certain card type or color, that player isn't required to find some or all of those cards
        // even if they're present in that zone.
        this.setRequired(!filter.hasPredicates());
        this.withNotTarget(true);
        this.librarySearchLimit = Integer.MAX_VALUE;
    }

    protected TargetCardInLibrary(final TargetCardInLibrary target) {
        super(target);
        this.librarySearchLimit = target.librarySearchLimit;
    }

    @Override
    public boolean choose(Outcome outcome, UUID playerId, UUID targetPlayerId, Ability source, Game game) { // TODO: wtf sourceId named as targetPlayerId?!
        Player player = game.getPlayer(playerId);
        Player targetPlayer = game.getPlayer(targetPlayerId);
        if (targetPlayer == null) {
            targetPlayer = player;
        }

        if (player == null) {
            return false;
        }

        List<Card> cards;
        if (librarySearchLimit == Integer.MAX_VALUE) {
            cards = targetPlayer.getLibrary().getCards(game);
        } else {
            cards = new ArrayList<>(targetPlayer.getLibrary().getTopCards(game, librarySearchLimit));
        }
        cards.sort(Comparator.comparing(MageObject::getName));
        Cards cardsId = new CardsImpl();
        cards.forEach(cardsId::add);

        UUID abilityControllerId = this.getAffectedAbilityControllerId(playerId);

        chosen = false;
        do {
            int prevTargetsCount = this.getTargets().size();

            // stop by disconnect
            if (!player.canRespond()) {
                break;
            }

            // stop by cancel/done
            // TODO: need research - why it used chooseTarget instead choose? Need random and other options?
            //  someday must be replaced by player.choose (require whole tests fix from addTarget to setChoice)
            if (!player.chooseTarget(outcome, cardsId, this, source, game)) {
                return chosen;
            }

            chosen = isChosen(game);

            // stop by full complete
            if (isChoiceCompleted(abilityControllerId, source, game, null)) {
                break;
            }

            // stop by nothing to use (actual for human and done button)
            if (prevTargetsCount == this.getTargets().size()) {
                break;
            }

            // can select next target
        } while (true);

        chosen = isChosen(game);
        return this.getTargets().size() > 0;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getPlayer(source.getControllerId()).getLibrary().getCard(id, game);
        return filter.match(card, source.getControllerId(), source, game);
    }

    @Override
    public TargetCardInLibrary copy() {
        return new TargetCardInLibrary(this);
    }

    @Override
    public void setMinNumberOfTargets(int minNumberOfTargets) {
        this.minNumberOfTargets = minNumberOfTargets;
    }

    public void setCardLimit(int librarySearchLimit) {
        this.librarySearchLimit = librarySearchLimit;
    }

}

