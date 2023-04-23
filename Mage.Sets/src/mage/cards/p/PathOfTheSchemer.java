package mage.cards.p;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.WillOfThePlaneswalkersEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathOfTheSchemer extends CardImpl {

    public PathOfTheSchemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Each player mills two cards. Then you put a creature card from a graveyard onto the battlefield under your control. It's an artifact in addition to its other types.
        this.getSpellAbility().addEffect(new MillCardsEachPlayerEffect(2, TargetController.EACH_PLAYER));
        this.getSpellAbility().addEffect(new PathOfTheSchemerEffect());

        // Will of the Planeswalkers -- Starting with you, each player votes for planeswalk or chaos. If planeswalk gets more votes, planeswalk. If chaos gets more votes or the vote is tied, chaos ensues.
        this.getSpellAbility().addEffect(new WillOfThePlaneswalkersEffect());
    }

    private PathOfTheSchemer(final PathOfTheSchemer card) {
        super(card);
    }

    @Override
    public PathOfTheSchemer copy() {
        return new PathOfTheSchemer(this);
    }
}

class PathOfTheSchemerEffect extends OneShotEffect {

    PathOfTheSchemerEffect() {
        super(Outcome.Benefit);
        staticText = "Then you put a creature card from a graveyard onto the battlefield " +
                "under your control. It's an artifact in addition to its other types";
    }

    private PathOfTheSchemerEffect(final PathOfTheSchemerEffect effect) {
        super(effect);
    }

    @Override
    public PathOfTheSchemerEffect copy() {
        return new PathOfTheSchemerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        player.choose(Outcome.PutCreatureInPlay, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        game.addEffect(new AddCardTypeTargetEffect(Duration.Custom, CardType.ARTIFACT)
                .setTargetPointer(new FixedTarget(new MageObjectReference(card, game, 1))), source);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
