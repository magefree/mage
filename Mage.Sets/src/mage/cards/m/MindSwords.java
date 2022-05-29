
package mage.cards.m;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class MindSwords extends CardImpl {

    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent("If you control a Swamp");

    static {
        filterSwamp.add(SubType.SWAMP.getPredicate());
    }

    public MindSwords(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // If you control a Swamp, you may sacrifice a creature rather than pay Mind Swords's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)),
                new PermanentsOnTheBattlefieldCondition(filterSwamp), null
        ));

        // Each player exiles two cards from their hand.
        this.getSpellAbility().addEffect(new MindSwordsEffect());
    }

    private MindSwords(final MindSwords card) {
        super(card);
    }

    @Override
    public MindSwords copy() {
        return new MindSwords(this);
    }
}

class MindSwordsEffect extends OneShotEffect {

    MindSwordsEffect() {
        super(Outcome.Exile);
        this.staticText = "Each player exiles two cards from their hand.";
    }

    MindSwordsEffect(final MindSwordsEffect effect) {
        super(effect);
    }

    @Override
    public MindSwordsEffect copy() {
        return new MindSwordsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Store for each player the cards to exile, that's important because all exile shall happen at the same time
        Map<UUID, Cards> cardsToExile = new HashMap<>();
        // Each player chooses 2 cards to discard
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            int numberOfCardsToExile = Math.min(2, player.getHand().size());
            Cards cards = new CardsImpl();

            Target target = new TargetCardInHand(numberOfCardsToExile, new FilterCard());

            player.chooseTarget(Outcome.Exile, target, source, game);
            cards.addAll(target.getTargets());
            cardsToExile.put(playerId, cards);
        }
        // Exile all chosen cards
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Cards cardsPlayerChoseToExile = cardsToExile.get(playerId);
            if (cardsPlayerChoseToExile == null) {
                continue;
            }
            player.moveCards(cardsPlayerChoseToExile.getCards(game), Zone.EXILED, source, game);
        }
        return true;
    }
}
