package mage.cards.l;

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
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LunarInsight extends CardImpl {

    public LunarInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Draw a card for each different mana value among nonland permanents you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(LunarInsightValue.instance));
        this.getSpellAbility().addHint(LunarInsightHint.instance);
    }

    private LunarInsight(final LunarInsight card) {
        super(card);
    }

    @Override
    public LunarInsight copy() {
        return new LunarInsight(this);
    }
}

enum LunarInsightValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .map(MageObject::getManaValue)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public LunarInsightValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "different mana value among nonland permanents you control";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum LunarInsightHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        List<String> powers = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND,
                        ability.getControllerId(), ability, game
                )
                .stream()
                .mapToInt(MageObject::getManaValue)
                .distinct()
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
        return "Different mana values among nonland permanents you control: " + powers.size()
                + (powers.size() > 0 ? " (" + String.join(", ", powers) + ')' : "");
    }

    @Override
    public LunarInsightHint copy() {
        return this;
    }
}
