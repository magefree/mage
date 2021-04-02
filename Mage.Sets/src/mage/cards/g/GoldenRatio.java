package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GoldenRatio extends CardImpl {

    public GoldenRatio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{U}");

        // Draw a card for each different power among creatures you control.
        this.getSpellAbility().addEffect(new GoldenRatioEffect());
        this.getSpellAbility().addHint(GoldenRatioHint.instance);
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

enum GoldenRatioHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        List<Integer> values = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        ability.getControllerId(), ability.getSourceId(), game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        String message = "" + values.size();
        if (values.size() > 0) {
            message += " (";
            message += values.stream().map(i -> "" + i).reduce((a, b) -> a + ", " + b).orElse("");
            message += ')';
        }
        return "Different powers among creatures you control: " + message;
    }

    @Override
    public GoldenRatioHint copy() {
        return instance;
    }
}