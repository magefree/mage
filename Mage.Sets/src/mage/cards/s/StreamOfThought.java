package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StreamOfThought extends CardImpl {

    public StreamOfThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Target player puts the top four cards of their library into their graveyard. You shuffle up to four cards from your graveyard into your library.
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(4));
        this.getSpellAbility().addEffect(new StreamOfThoughtEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Replicate {2}{U}{U}
        this.addAbility(new ReplicateAbility("{2}{U}{U}"));
    }

    private StreamOfThought(final StreamOfThought card) {
        super(card);
    }

    @Override
    public StreamOfThought copy() {
        return new StreamOfThought(this);
    }
}

class StreamOfThoughtEffect extends OneShotEffect {

    StreamOfThoughtEffect() {
        super(Outcome.Benefit);
        staticText = "You shuffle up to four cards from your graveyard into your library.";
    }

    private StreamOfThoughtEffect(final StreamOfThoughtEffect effect) {
        super(effect);
    }

    @Override
    public StreamOfThoughtEffect copy() {
        return new StreamOfThoughtEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(0, 4);
        target.setNotTarget(true);
        if (!player.choose(outcome, player.getGraveyard(), target, game)) {
            return false;
        }
        Cards cards = new CardsImpl(target.getTargets());
        player.putCardsOnTopOfLibrary(cards, game, source, false);
        player.shuffleLibrary(source, game);
        return true;
    }
}