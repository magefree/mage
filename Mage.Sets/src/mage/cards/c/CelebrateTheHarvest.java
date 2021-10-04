package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.CovenHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CelebrateTheHarvest extends CardImpl {

    public CelebrateTheHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to X basic land cards, where X is the number of different powers among creatures you control. Put those cards onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new CelebrateTheHarvestEffect());
        this.getSpellAbility().addHint(CovenHint.instance);
    }

    private CelebrateTheHarvest(final CelebrateTheHarvest card) {
        super(card);
    }

    @Override
    public CelebrateTheHarvest copy() {
        return new CelebrateTheHarvest(this);
    }
}

class CelebrateTheHarvestEffect extends OneShotEffect {

    CelebrateTheHarvestEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X basic land cards, where X is the number of different powers " +
                "among creatures you control. Put those cards onto the battlefield tapped, then shuffle";
    }

    private CelebrateTheHarvestEffect(final CelebrateTheHarvestEffect effect) {
        super(effect);
    }

    @Override
    public CelebrateTheHarvestEffect copy() {
        return new CelebrateTheHarvestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int powerCount = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source.getSourceId(), game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .distinct()
                .map(x -> 1)
                .sum();
        TargetCardInLibrary target = new TargetCardInLibrary(0, powerCount, StaticFilters.FILTER_CARD_BASIC_LAND);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(cardId -> player.getLibrary().getCard(cardId, game))
                .forEach(cards::add);
        player.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, true, null
        );
        player.shuffleLibrary(source, game);
        return true;
    }
}
