package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.LegendaryCreatureCostAdjuster;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoseijuWhoEndures extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact, enchantment, or nonbasic land an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.and(
                        Predicates.not(SuperType.BASIC.getPredicate()),
                        CardType.LAND.getPredicate()
                )
        ));
    }

    public BoseijuWhoEndures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Channel — {1}{G}, Discard Boseiju, Who Endures: Destroy target artifact, enchantment, or nonbasic land an opponent controls. That player may search their library for a land card with a basic land type, put it onto the battlefield, then shuffle. This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new ChannelAbility("{1}{G}", new BoseijuWhoEnduresEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.setCostAdjuster(LegendaryCreatureCostAdjuster.instance);
        this.addAbility(ability);
    }

    private BoseijuWhoEndures(final BoseijuWhoEndures card) {
        super(card);
    }

    @Override
    public BoseijuWhoEndures copy() {
        return new BoseijuWhoEndures(this);
    }
}

class BoseijuWhoEnduresEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterLandCard("land card with a basic land type");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    BoseijuWhoEnduresEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target artifact, enchantment, or nonbasic land an opponent controls. " +
                "That player may search their library for a land card with a basic land type, " +
                "put it onto the battlefield, then shuffle. " +
                "This ability costs {1} less to activate for each legendary creature you control";
    }

    private BoseijuWhoEnduresEffect(final BoseijuWhoEnduresEffect effect) {
        super(effect);
    }

    @Override
    public BoseijuWhoEnduresEffect copy() {
        return new BoseijuWhoEnduresEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        permanent.destroy(source, game);
        if (!player.chooseUse(Outcome.PutCardInPlay, "Search your library for a land card?", source, game)) {
            return true;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
