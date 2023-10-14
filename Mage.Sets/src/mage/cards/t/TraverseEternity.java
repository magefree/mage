package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TraverseEternity extends CardImpl {

    public TraverseEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Draw cards equal to the highest mana value among historic permanents you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(TraverseEternityValue.instance)
                .setText("draw cards equal to the highest mana value among historic permanents you control"));
        this.getSpellAbility().addHint(TraverseEternityValue.getHint());
    }

    private TraverseEternity(final TraverseEternity card) {
        super(card);
    }

    @Override
    public TraverseEternity copy() {
        return new TraverseEternity(this);
    }
}

enum TraverseEternityValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Highest mana value among your historic permanents", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .filter(permanent -> permanent.isHistoric(game))
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public TraverseEternityValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
