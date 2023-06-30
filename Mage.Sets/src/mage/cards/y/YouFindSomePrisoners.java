package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouFindSomePrisoners extends CardImpl {

    public YouFindSomePrisoners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one —
        // • Break Their Chains — Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Break Their Chains");

        // • Interrogate Them — Exile the top three cards of target opponent's library. Choose one of them. Until the end of your next turn, you may play that card, and you may spend mana as through it were mana of any color to cast it.
        Mode mode = new Mode(new YouFindSomePrisonersEffect());
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode.withFlavorWord("Interrogate Them"));
    }

    private YouFindSomePrisoners(final YouFindSomePrisoners card) {
        super(card);
    }

    @Override
    public YouFindSomePrisoners copy() {
        return new YouFindSomePrisoners(this);
    }
}

class YouFindSomePrisonersEffect extends OneShotEffect {

    YouFindSomePrisonersEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top three cards of target opponent's library. " +
                "Choose one of them. Until the end of your next turn, you may play that card, " +
                "and you may spend mana as though it were mana of any color to cast it";
    }

    private YouFindSomePrisonersEffect(final YouFindSomePrisonersEffect effect) {
        super(effect);
    }

    @Override
    public YouFindSomePrisonersEffect copy() {
        return new YouFindSomePrisonersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player == null || opponent == null) {
            return false;
        }
        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 3));
        player.moveCards(cards, Zone.EXILED, source, game);
        TargetCardInExile target = new TargetCardInExile(StaticFilters.FILTER_CARD);
        target.setNotTarget(true);
        player.choose(Outcome.PlayForFree, cards, target, source, game);
        Card card = cards.get(target.getFirstTarget(), game);
        if (card != null) {
            CardUtil.makeCardPlayable(game, source, card, Duration.UntilEndOfYourNextTurn, true);
        }
        return true;
    }
}
