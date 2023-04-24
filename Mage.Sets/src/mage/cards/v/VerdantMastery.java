package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VerdantMastery extends CardImpl {

    public VerdantMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // You may pay {3}{G} rather than pay this spell's mana cost.
        Ability costAbility = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{3}{G}"));
        this.addAbility(costAbility);

        // Search your library for up to four basic land cards and reveal them. Put one of them onto the battlefield tapped under an opponent's control if the {3}{G} cost was paid. Put two of them onto the battlefield tapped under your control and the rest into your hand. Then shuffle.
        this.getSpellAbility().addEffect(new VerdantMasteryEffect(costAbility.getOriginalId()));
    }

    private VerdantMastery(final VerdantMastery card) {
        super(card);
    }

    @Override
    public VerdantMastery copy() {
        return new VerdantMastery(this);
    }
}

class VerdantMasteryEffect extends OneShotEffect {

    private final UUID alternativeCostOriginalID;

    VerdantMasteryEffect(UUID alternativeCostOriginalID) {
        super(Outcome.Detriment);
        staticText = "search your library for up to four basic land cards and reveal them. Put one of them " +
                "onto the battlefield tapped under an opponent's control if the {3}{G} cost was paid. Put two of " +
                "them onto the battlefield tapped under your control and the rest into your hand. Then shuffle";
        this.alternativeCostOriginalID = alternativeCostOriginalID;
    }

    private VerdantMasteryEffect(VerdantMasteryEffect effect) {
        super(effect);
        this.alternativeCostOriginalID = effect.alternativeCostOriginalID;
    }

    @Override
    public VerdantMasteryEffect copy() {
        return new VerdantMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 4, StaticFilters.FILTER_CARD_BASIC_LAND);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.revealCards(source, cards, game);
        if (cards.isEmpty()) {
            player.shuffleLibrary(source, game);
            return true;
        }
        if (AlternativeCostSourceAbility.getActivatedStatus(
                game, source, this.alternativeCostOriginalID, false
        )) {
            TargetOpponent targetOpponent = new TargetOpponent(true);
            player.chooseTarget(Outcome.DrawCard, targetOpponent, source, game);
            Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
            if (opponent != null) {
                target = new TargetCardInLibrary(1, StaticFilters.FILTER_CARD_BASIC_LAND);
                target.setRequired(true);
                target.withChooseHint("to give to " + opponent.getName());
                player.choose(outcome, cards, target, source, game);
                Card card = game.getCard(target.getFirstTarget());
                opponent.moveCards(
                        card, Zone.BATTLEFIELD, source, game, true,
                        false, false, null
                );
            }
        }
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);
        if (cards.isEmpty()) {
            player.shuffleLibrary(source, game);
            return true;
        }
        target = new TargetCardInLibrary(Math.min(cards.size(), 2), StaticFilters.FILTER_CARD_BASIC_LAND);
        target.setRequired(true);
        player.choose(outcome, cards, target, source, game);
        player.moveCards(
                new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source,
                game, true, false, false, null
        );
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);
        player.moveCards(cards, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
