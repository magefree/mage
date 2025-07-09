package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PrimalCommand extends CardImpl {

    public PrimalCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Target player gains 7 life;
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // or put target noncreature permanent on top of its owner's library;
        this.getSpellAbility().getModes().addMode(new Mode(new PutOnLibraryTargetEffect(true))
                .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_CREATURE)));

        // or target player shuffles their graveyard into their library;
        this.getSpellAbility().getModes().addMode(new Mode(new PrimalCommandShuffleGraveyardEffect())
                .addTarget(new TargetPlayer()));

        // or search your library for a creature card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().getModes().addMode(new Mode(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), true
        )));
    }

    private PrimalCommand(final PrimalCommand card) {
        super(card);
    }

    @Override
    public PrimalCommand copy() {
        return new PrimalCommand(this);
    }
}

class PrimalCommandShuffleGraveyardEffect extends OneShotEffect {

    PrimalCommandShuffleGraveyardEffect() {
        super(Outcome.Neutral);
        this.staticText = "target player shuffles their graveyard into their library";
    }

    private PrimalCommandShuffleGraveyardEffect(final PrimalCommandShuffleGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public PrimalCommandShuffleGraveyardEffect copy() {
        return new PrimalCommandShuffleGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        player.shuffleCardsToLibrary(player.getGraveyard(), game, source);
        return true;
    }
}
