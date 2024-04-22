
package mage.cards.w;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class WaveOfVitriol extends CardImpl {

    public WaveOfVitriol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");

        // Each player sacrifices all artifacts, enchantments, and nonbasic lands they control. For each land sacrificed this way, its controller may search their library for a basic land card and put it onto the battlefield tapped. Then each player who searched their library this way shuffles it.
        this.getSpellAbility().addEffect(new WaveOfVitriolEffect());

    }

    private WaveOfVitriol(final WaveOfVitriol card) {
        super(card);
    }

    @Override
    public WaveOfVitriol copy() {
        return new WaveOfVitriol(this);
    }
}

class WaveOfVitriolEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.and(
                        CardType.LAND.getPredicate(),
                        Predicates.not(SuperType.BASIC.getPredicate())
                )
        ));
    }

    public WaveOfVitriolEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player sacrifices all artifacts, enchantments, and nonbasic lands they control. For each land sacrificed this way, its controller may search their library for a basic land card and put it onto the battlefield tapped. Then each player who searched their library this way shuffles";
    }

    private WaveOfVitriolEffect(final WaveOfVitriolEffect effect) {
        super(effect);
    }

    @Override
    public WaveOfVitriolEffect copy() {
        return new WaveOfVitriolEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Player, Integer> sacrificedLands = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = 0;
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        if (permanent.sacrifice(source, game) && permanent.isLand(game)) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        sacrificedLands.put(player, count);
                    }
                }
            }
            game.getState().processAction(game);
            Cards toBattlefield = new CardsImpl();
            Set<Player> playersToShuffle = new LinkedHashSet<>();
            for (Map.Entry<Player, Integer> entry : sacrificedLands.entrySet()) {
                if (entry.getKey().chooseUse(Outcome.PutLandInPlay, "Search your library for up to " + entry.getValue() + " basic lands?", source, game)) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, entry.getValue(), StaticFilters.FILTER_CARD_BASIC_LAND);
                    if (entry.getKey().searchLibrary(target, source, game)) {
                        if (!target.getTargets().isEmpty()) {
                            toBattlefield.addAll(target.getTargets());
                            playersToShuffle.add(entry.getKey());
                        }
                    }
                }
            }

            controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, true, false, true, null);
            for (Player player : playersToShuffle) {
                player.shuffleLibrary(source, game);
            }

            return true;
        }
        return false;
    }
}
