
package mage.cards.f;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class FallOfTheThran extends CardImpl {

    public FallOfTheThran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);
        // I — Destroy all lands.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DestroyAllEffect(StaticFilters.FILTER_LANDS));
        // II, III — Each player returns two land cards from their graveyard to the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, new FallOfTheThranReturnEffect());
        this.addAbility(sagaAbility);
    }

    private FallOfTheThran(final FallOfTheThran card) {
        super(card);
    }

    @Override
    public FallOfTheThran copy() {
        return new FallOfTheThran(this);
    }
}

class FallOfTheThranReturnEffect extends OneShotEffect {

    public FallOfTheThranReturnEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "each player returns two land cards from their graveyard to the battlefield";
    }

    public FallOfTheThranReturnEffect(final FallOfTheThranReturnEffect effect) {
        super(effect);
    }

    @Override
    public FallOfTheThranReturnEffect copy() {
        return new FallOfTheThranReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Map<UUID, Set<Card>> toBattlefield = new LinkedHashMap<>();
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if(player != null) {
                    TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(2, 2, StaticFilters.FILTER_CARD_LAND);
                    target.setNotTarget(true);
                    target.setTargetController(playerId);
                    if (target.canChoose(playerId, source, game)) {
                        player.choose(outcome, target, source, game);
                        if (target.getTargets().size() == 2) {
                            toBattlefield.put(playerId, new CardsImpl(target.getTargets()).getCards(game));
                        }
                    }
                }
            }

            for (Map.Entry<UUID, Set<Card>> entry : toBattlefield.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    player.moveCards(entry.getValue(), Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
