
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.token.MyrToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public final class MyrIncubator extends CardImpl {

    public MyrIncubator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {6}, {tap}, Sacrifice Myr Incubator: Search your library for any number of artifact cards, exile them,
        //                                      then put that many 1/1 colorless Myr artifact creature tokens onto the battlefield.
        //                                      Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MyrIncubatorEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private MyrIncubator(final MyrIncubator card) {
        super(card);
    }

    @Override
    public MyrIncubator copy() {
        return new MyrIncubator(this);
    }
}

class MyrIncubatorEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }
    
    int tokensToCreate;

    MyrIncubatorEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.Neutral);
        staticText = "Search your library for any number of artifact cards, exile them, " +
                     "then create that many 1/1 colorless Myr artifact creature tokens. " +
                     "Then shuffle";
    }

    MyrIncubatorEffect(final MyrIncubatorEffect effect) {
        super(effect);
        this.tokensToCreate = effect.tokensToCreate;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        if (!controller.searchLibrary(target, source, game)) {
            return false;
        }

        if (!target.getTargets().isEmpty()) {
            tokensToCreate = target.getTargets().size();
            controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
            CreateTokenEffect effect = new CreateTokenEffect(new MyrToken(), tokensToCreate);
            effect.apply(game, source);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public MyrIncubatorEffect copy() {
        return new MyrIncubatorEffect(this);
    }
}
