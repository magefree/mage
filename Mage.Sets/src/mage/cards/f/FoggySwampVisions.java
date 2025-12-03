package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.costs.common.WaterbendXCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoggySwampVisions extends CardImpl {

    public FoggySwampVisions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // As an additional cost to cast this spell, waterbend {X}.
        this.getSpellAbility().addCost(new WaterbendXCost());

        // Exile X target creature cards from graveyards. For each creature card exiled this way, create a token that's a copy of it. At the beginning of your next end step, sacrifice those tokens.
        this.getSpellAbility().addEffect(new FoggySwampVisionsEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURES));
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());
    }

    private FoggySwampVisions(final FoggySwampVisions card) {
        super(card);
    }

    @Override
    public FoggySwampVisions copy() {
        return new FoggySwampVisions(this);
    }
}

class FoggySwampVisionsEffect extends OneShotEffect {

    FoggySwampVisionsEffect() {
        super(Outcome.Benefit);
        staticText = "exile X target creature cards from graveyards. For each creature card exiled this way, " +
                "create a token that's a copy of it. At the beginning of your next end step, sacrifice those tokens.";
    }

    private FoggySwampVisionsEffect(final FoggySwampVisionsEffect effect) {
        super(effect);
    }

    @Override
    public FoggySwampVisionsEffect copy() {
        return new FoggySwampVisionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        Set<Permanent> permanents = new HashSet<>();
        for (Card card : cards.getCards(game)) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.apply(game, source);
            permanents.addAll(effect.getAddedPermanents());
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice those tokens")
                        .setTargetPointer(new FixedTargets(permanents, game)),
                TargetController.YOU
        ), source);
        return true;
    }
}
