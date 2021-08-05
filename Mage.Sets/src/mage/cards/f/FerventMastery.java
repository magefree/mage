package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
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
public final class FerventMastery extends CardImpl {

    public FerventMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // You may pay {2}{R}{R} rather than pay this spell's mana cost.
        Ability costAbility = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{2}{R}{R}"));
        this.addAbility(costAbility);

        // If the {2}{R}{R} cost was paid, an opponent discards any number of cards, then draws that many cards.
        this.getSpellAbility().addEffect(new FerventMasteryAlternativeCostEffect(costAbility.getOriginalId()));

        // Search your library for up to three cards, put them into your hand, shuffle, then discard three cards at random.
        this.getSpellAbility().addEffect(new FerventMasteryEffect());
    }

    private FerventMastery(final FerventMastery card) {
        super(card);
    }

    @Override
    public FerventMastery copy() {
        return new FerventMastery(this);
    }
}

class FerventMasteryAlternativeCostEffect extends OneShotEffect {

    private final UUID alternativeCostOriginalID;

    FerventMasteryAlternativeCostEffect(UUID alternativeCostOriginalID) {
        super(Outcome.Detriment);
        staticText = "if the {2}{R}{R} cost was paid, an opponent discards any number of cards, " +
                "then draws that many cards.<br>";
        this.alternativeCostOriginalID = alternativeCostOriginalID;
    }

    private FerventMasteryAlternativeCostEffect(FerventMasteryAlternativeCostEffect effect) {
        super(effect);
        this.alternativeCostOriginalID = effect.alternativeCostOriginalID;
    }

    @Override
    public FerventMasteryAlternativeCostEffect copy() {
        return new FerventMasteryAlternativeCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!AlternativeCostSourceAbility.getActivatedStatus(
                game, source, this.alternativeCostOriginalID, false
        )) {
            return false;
        }

        Player player = game.getPlayer(source.getControllerId());
        TargetOpponent targetOpponent = new TargetOpponent(true);
        if (!player.chooseTarget(Outcome.DrawCard, targetOpponent, source, game)) {
            return false;
        }
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        int discarded = opponent.discard(0, Integer.MAX_VALUE, false, source, game).size();
        opponent.drawCards(discarded, source, game);
        return true;
    }
}

class FerventMasteryEffect extends OneShotEffect {

    FerventMasteryEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for up to three cards, put them into your hand, " +
                "shuffle, then discard three cards at random.";
    }

    private FerventMasteryEffect(final FerventMasteryEffect effect) {
        super(effect);
    }

    @Override
    public FerventMasteryEffect copy() {
        return new FerventMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(3, StaticFilters.FILTER_CARD);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);
        player.moveCards(cards, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        player.discard(3, true, false, source, game);
        return true;
    }
}
