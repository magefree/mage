package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.replacement.CreaturesAreExiledOnDeathReplacementEffect;
import mage.cards.Cards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Beast44TrampleToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;

/**
 *
 * @author muz
 */
public final class GarrukVeiledButcher extends CardImpl {

    public GarrukVeiledButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);
        this.setStartingLoyalty(0);

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(
            new CreaturesAreExiledOnDeathReplacementEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE)
        ));

        // +2: Up to one target creature gets -4/-1 until your next turn.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(
            -4, -1, Duration.UntilYourNextTurn
        ).setText("Up to one target creature gets -4/-1 until your next turn."), 2);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −2: Each player sacrifices a creature of their choice. If you sacrificed a creature this way, create a 4/4 green Beast creature token with trample.
        this.addAbility(new LoyaltyAbility(new GarrukVeiledButcherSacrificeEffect(), -2));

        // −3: Each opponent discards two cards. For each opponent who didn't discard two nonland cards this way, you draw a card.
        this.addAbility(new LoyaltyAbility(new GarrukVeiledButcherDiscardEffect(), -3));
    }

    private GarrukVeiledButcher(final GarrukVeiledButcher card) {
        super(card);
    }

    @Override
    public GarrukVeiledButcher copy() {
        return new GarrukVeiledButcher(this);
    }
}

class GarrukVeiledButcherSacrificeEffect extends OneShotEffect {

    GarrukVeiledButcherSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "Each player sacrifices a creature of their choice. "
            + "If you sacrificed a creature this way, create a 4/4 green Beast creature token with trample.";
    }

    private GarrukVeiledButcherSacrificeEffect(final GarrukVeiledButcherSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public GarrukVeiledButcherSacrificeEffect copy() {
        return new GarrukVeiledButcherSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE).apply(game, source);
        List<Permanent> sacrificed = SacrificeAllEffect.getSacrificedPermanentsList(source.getSourceId(), game, false);
        if (sacrificed == null) {
            return false;
        }
        boolean youSacrificed = sacrificed.stream().anyMatch(permanent -> source.isControlledBy(permanent.getControllerId()));
        if (youSacrificed) {
            new Beast44TrampleToken().putOntoBattlefield(1, game, source, source.getControllerId());
        }
        return true;
    }
}

class GarrukVeiledButcherDiscardEffect extends OneShotEffect {

    GarrukVeiledButcherDiscardEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent discards two cards. "
            + "For each opponent who didn't discard two nonland cards this way, you draw a card.";
    }

    private GarrukVeiledButcherDiscardEffect(final GarrukVeiledButcherDiscardEffect effect) {
        super(effect);
    }

    @Override
    public GarrukVeiledButcherDiscardEffect copy() {
        return new GarrukVeiledButcherDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int cardsToDraw = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId(), true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            Cards discarded = opponent.discard(2, false, false, source, game);
            if (discarded.count(StaticFilters.FILTER_CARD_NON_LAND, game) < 2) {
                cardsToDraw++;
            }
        }
        if (cardsToDraw > 0) {
            controller.drawCards(cardsToDraw, source, game);
        }
        return true;
    }
}
