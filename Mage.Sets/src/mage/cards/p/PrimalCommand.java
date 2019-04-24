
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class PrimalCommand extends CardImpl {

    private static final FilterPermanent filterNonCreature = new FilterPermanent("noncreature permanent");
    static {
        filterNonCreature.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public PrimalCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");


        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Target player gains 7 life;
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // or put target noncreature permanent on top of its owner's library;
        Mode mode = new Mode();
        mode.getEffects().add(new PutOnLibraryTargetEffect(true));
        Target target = new TargetPermanent(filterNonCreature);
        mode.getTargets().add(target);
        this.getSpellAbility().getModes().addMode(mode);
        // or target player shuffles their graveyard into their library;
        mode = new Mode();
        mode.getEffects().add(new PrimalCommandShuffleGraveyardEffect());
        mode.getTargets().add(new TargetPlayer());
        this.getSpellAbility().getModes().addMode(mode);        
        // or search your library for a creature card, reveal it, put it into your hand, then shuffle your library.
        mode = new Mode();
        mode.getEffects().add(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterCreatureCard()), true, true));
        this.getSpellAbility().getModes().addMode(mode);
        
    }

    public PrimalCommand(final PrimalCommand card) {
        super(card);
    }

    @Override
    public PrimalCommand copy() {
        return new PrimalCommand(this);
    }
}

class PrimalCommandShuffleGraveyardEffect extends OneShotEffect {

    public PrimalCommandShuffleGraveyardEffect() {
        super(Outcome.Neutral);
        this.staticText = "target player shuffles their graveyard into their library";
    }

    public PrimalCommandShuffleGraveyardEffect(final PrimalCommandShuffleGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public PrimalCommandShuffleGraveyardEffect copy() {
        return new PrimalCommandShuffleGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            for (Card card: player.getGraveyard().getCards(game)) {
                player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }                 
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
