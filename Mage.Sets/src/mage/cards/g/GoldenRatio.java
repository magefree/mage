package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;

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
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(GoldenRatioValue.instance));
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

enum GoldenRatioValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility.getSourceId(), game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .distinct()
                .map(x -> 1)
                .sum();
    }

    @Override
    public GoldenRatioValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "different power among creatures you control";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum GoldenRatioHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        List<String> values = game
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
                .map(String::valueOf)
                .collect(Collectors.toList());
        return "Different powers among creatures you control: " + +values.size()
                + (values.size() > 0 ? " (" + String.join(", ", values) + ')' : "");
    }

    @Override
    public GoldenRatioHint copy() {
        return this;
    }
}
