package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public final class GhostQuarter extends CardImpl {

    public GhostQuarter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {T}, Sacrifice Ghost Quarter: Destroy target land. Its controller may search their library for a basic land card, put it onto the battlefield, then shuffle their library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetLandPermanent());
        ability.addEffect(new GhostQuarterEffect());
        this.addAbility(ability);
    }

    private GhostQuarter(final GhostQuarter card) {
        super(card);
    }

    @Override
    public GhostQuarter copy() {
        return new GhostQuarter(this);
    }
}

class GhostQuarterEffect extends OneShotEffect {

    public GhostQuarterEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Its controller may search their library for a basic land card, put it onto the battlefield, then shuffle";
    }

    public GhostQuarterEffect(final GhostQuarterEffect effect) {
        super(effect);
    }

    @Override
    public GhostQuarterEffect copy() {
        return new GhostQuarterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null && controller.chooseUse(Outcome.PutLandInPlay, "Search for a basic land, put it onto the battlefield, and then shuffle?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
                if (controller.searchLibrary(target, source, game)) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
