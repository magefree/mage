
package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author L_J
 */
public final class RishadanPawnshop extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanent you control");
    
    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RishadanPawnshop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Shuffle target nontoken permanent you control into its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RishadanPawnshopShuffleIntoLibraryEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

    }

    private RishadanPawnshop(final RishadanPawnshop card) {
        super(card);
    }

    @Override
    public RishadanPawnshop copy() {
        return new RishadanPawnshop(this);
    }
}

class RishadanPawnshopShuffleIntoLibraryEffect extends OneShotEffect {

    public RishadanPawnshopShuffleIntoLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "The owner of target nontoken permanent you control shuffles it into their library";
    }

    public RishadanPawnshopShuffleIntoLibraryEffect(final RishadanPawnshopShuffleIntoLibraryEffect effect) {
        super(effect);
    }

    @Override
    public RishadanPawnshopShuffleIntoLibraryEffect copy() {
        return new RishadanPawnshopShuffleIntoLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            if (owner != null) {
                owner.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, true, true);
                owner.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }
}
