package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraspingTentacles extends CardImpl {

    public GraspingTentacles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // Target opponent mills eight cards. You may put an artifact card from that player's graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(8));
        this.getSpellAbility().addEffect(new GraspingTentaclesEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private GraspingTentacles(final GraspingTentacles card) {
        super(card);
    }

    @Override
    public GraspingTentacles copy() {
        return new GraspingTentacles(this);
    }
}

class GraspingTentaclesEffect extends OneShotEffect {

    GraspingTentaclesEffect() {
        super(Outcome.Benefit);
        staticText = "you may put an artifact card from that player's " +
                "graveyard onto the battlefield under your control";
    }

    private GraspingTentaclesEffect(final GraspingTentaclesEffect effect) {
        super(effect);
    }

    @Override
    public GraspingTentaclesEffect copy() {
        return new GraspingTentaclesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(
                0, 1, StaticFilters.FILTER_CARD_ARTIFACT, true
        );
        controller.choose(Outcome.PutCardInPlay, opponent.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
