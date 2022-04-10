package mage.cards.e;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public final class EvolvingDoor extends CardImpl {

    public EvolvingDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        // {1}, {T}, Sacrifice a creature: Count the colors of the sacrificed creature, then search your library for a creature card that's exactly that many colors plus one. Exile that card, then shuffle. You may cast the exiled card. Activate only as a sorcery.
        Ability ability = new SimpleActivatedAbility(new EvolvingDoorEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private EvolvingDoor(final EvolvingDoor card) {
        super(card);
    }

    @Override
    public EvolvingDoor copy() {
        return new EvolvingDoor(this);
    }
}

class EvolvingDoorEffect extends OneShotEffect {

    private static final class EvolvingDoorPredicate implements Predicate<Card> {
        private final int count;

        private EvolvingDoorPredicate(int count) {
            this.count = count;
        }

        @Override
        public boolean apply(Card input, Game game) {
            return input.getColor(game).getColorCount() == count;
        }
    }

    private static final Map<Integer, FilterCard> filterMap = new HashMap<>();

    static {
        for (int count = 1; count <= 6; count++) {
            FilterCard filter = new FilterCard(
                    "creature card that's exactly " +
                            CardUtil.numberToText(count) +
                            " color" + (count > 0 ? "s" : "")
            );
            filter.add(new EvolvingDoorPredicate(count));
            filterMap.put(count, filter);
        }
    }

    EvolvingDoorEffect() {
        super(Outcome.Benefit);
        staticText = "count the colors of the sacrificed creature, then search your library for a creature card " +
                "that's exactly that many colors plus one. Exile that card, then shuffle. You may cast the exiled card";
    }

    private EvolvingDoorEffect(final EvolvingDoorEffect effect) {
        super(effect);
    }

    @Override
    public EvolvingDoorEffect copy() {
        return new EvolvingDoorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = CardUtil
                .castStream(
                        source.getCosts().stream(),
                        SacrificeTargetCost.class
                )
                .filter(Objects::nonNull)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .findFirst()
                .orElse(null);
        if (player == null || permanent == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(filterMap.get(permanent.getColor(game).getColorCount() + 1));
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        player.moveCards(card, Zone.EXILED, source, game);
        player.shuffleLibrary(source, game);
        if (card == null || !player.chooseUse(
                Outcome.PlayForFree, "Cast " + card.getName() + '?', source, game
        )) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(card, game, false),
                game, false, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        return true;
    }
}
