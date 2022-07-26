package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class DubiousChallenge extends CardImpl {

    public DubiousChallenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Look at the top ten cards of your library, exile up to two creature cards from among them, then shuffle your library. Target opponent may choose one of the exiled cards and put it onto the battlefield under their control. Put the rest onto the battlefield under your control.
        getSpellAbility().addEffect(new DubiousChallengeEffect());
        getSpellAbility().addTarget(new TargetOpponent());
        getSpellAbility().addEffect(new DubiousChallengeMoveToBattlefieldEffect());
        getSpellAbility().addEffect(new DubiousChallengeMoveToBattlefieldEffect());
    }

    private DubiousChallenge(final DubiousChallenge card) {
        super(card);
    }

    @Override
    public DubiousChallenge copy() {
        return new DubiousChallenge(this);
    }
}

class DubiousChallengeEffect extends OneShotEffect {

    public DubiousChallengeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top ten cards of your library, exile up to two creature cards from among them, then shuffle. Target opponent may choose one of the exiled cards and put it onto the battlefield under their control. Put the rest onto the battlefield under your control.";
    }

    public DubiousChallengeEffect(final DubiousChallengeEffect effect) {
        super(effect);
    }

    @Override
    public DubiousChallengeEffect copy() {
        return new DubiousChallengeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAll(controller.getLibrary().getTopCards(game, 10));
            controller.lookAtCards(sourceObject.getIdName(), topCards, game);
            TargetCard targetCreatures = new TargetCard(0, 2, Zone.LIBRARY, StaticFilters.FILTER_CARD_CREATURE);
            controller.choose(outcome, topCards, targetCreatures, game);
            Cards exiledCards = new CardsImpl(targetCreatures.getTargets());
            if (!exiledCards.isEmpty()) {
                controller.moveCards(exiledCards, Zone.EXILED, source, game);
                controller.shuffleLibrary(source, game);
                Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (opponent != null) {
                    TargetCard targetOpponentCreature = new TargetCard(0, 1, Zone.EXILED, StaticFilters.FILTER_CARD_CREATURE);
                    DubiousChallengeMoveToBattlefieldEffect opponentEffect = (DubiousChallengeMoveToBattlefieldEffect) source.getEffects().get(1);
                    DubiousChallengeMoveToBattlefieldEffect controllerEffect = (DubiousChallengeMoveToBattlefieldEffect) source.getEffects().get(2);
                    if (opponent.choose(outcome, exiledCards, targetOpponentCreature, game)) {
                        Card card = game.getCard(targetOpponentCreature.getFirstTarget());
                        if (card != null) {
                            opponentEffect.setPlayerAndCards(opponent, new CardsImpl(card));
                            exiledCards.remove(card);
                        }
                    }
                    if (!exiledCards.isEmpty()) {
                        controllerEffect.setPlayerAndCards(controller, exiledCards);
                    }
                }
            } else {
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}

class DubiousChallengeMoveToBattlefieldEffect extends OneShotEffect {

    private Cards cards;
    private Player player;

    public DubiousChallengeMoveToBattlefieldEffect() {
        super(Outcome.Benefit);
    }

    public DubiousChallengeMoveToBattlefieldEffect(final DubiousChallengeMoveToBattlefieldEffect effect) {
        super(effect);
        if (effect.cards != null) {
            this.cards = effect.cards.copy();
        }
        if (effect.player != null) {
            this.player = effect.player.copy();
        }
    }

    @Override
    public DubiousChallengeMoveToBattlefieldEffect copy() {
        return new DubiousChallengeMoveToBattlefieldEffect(this);
    }

    public void setPlayerAndCards(Player targetPlayer, Cards targetCards) {
        this.player = targetPlayer;
        this.cards = targetCards;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (cards == null || player == null) {
            return false;
        }

        return player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
