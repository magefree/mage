package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class PraetorsGrasp extends CardImpl {

    public PraetorsGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Search target opponent's library for a card and exile it face down. Then that player shuffles their library. You may look at and play that card for as long as it remains exiled.
        this.getSpellAbility().addEffect(new PraetorsGraspEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private PraetorsGrasp(final PraetorsGrasp card) {
        super(card);
    }

    @Override
    public PraetorsGrasp copy() {
        return new PraetorsGrasp(this);
    }
}

class PraetorsGraspEffect extends OneShotEffect {

    PraetorsGraspEffect() {
        super(Outcome.PlayForFree);
        staticText = "Search target opponent's library for a card and exile it "
                + "face down. Then that player shuffles. You may "
                + "look at and play that card for as long as it remains exiled";
    }

    private PraetorsGraspEffect(final PraetorsGraspEffect effect) {
        super(effect);
    }

    @Override
    public PraetorsGraspEffect copy() {
        return new PraetorsGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null
                && opponent != null
                && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary();
            if (controller.searchLibrary(target, source, game, opponent.getId())) {
                UUID targetId = target.getFirstTarget();
                Card card = opponent.getLibrary().getCard(targetId, game);
                if (card == null) {
                    return false;
                }
                new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.NONE)
                        .setTargetPointer(new FixedTarget(card, game))
                        .apply(game, source);
            }
            opponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}