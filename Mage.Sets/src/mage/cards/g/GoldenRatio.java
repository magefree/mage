package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldenRatio extends CardImpl {

    public GoldenRatio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{U}");

        // Draw a card for each different power among creatures you control.
        this.getSpellAbility().addEffect(new GoldenRatioEffect());
    }

    private GoldenRatio(final GoldenRatio card) {
        super(card);
    }

    @Override
    public GoldenRatio copy() {
        return new GoldenRatio(this);
    }
}

class GoldenRatioEffect extends OneShotEffect {

    GoldenRatioEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card for each different power among creatures you control";
    }

    private GoldenRatioEffect(final GoldenRatioEffect effect) {
        super(effect);
    }

    @Override
    public GoldenRatioEffect copy() {
        return new GoldenRatioEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int unique = game
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
        return player.drawCards(unique, source, game) > 0;
    }
}
