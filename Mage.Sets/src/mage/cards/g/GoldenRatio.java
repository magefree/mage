package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.CovenHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldenRatio extends CardImpl {

    public GoldenRatio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{U}");

        // Draw a card for each different power among creatures you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(GoldenRatioValue.instance));
        this.getSpellAbility().addHint(CovenHint.instance);
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
