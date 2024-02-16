package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReckonersBargain extends CardImpl {

    public ReckonersBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT));

        // You gain life equal to the sacrificed permanent's mana value. Draw two cards.
        this.getSpellAbility().addEffect(new GainLifeEffect(
                ReckonersBargainValue.instance, "you gain life " +
                "equal to the sacrificed permanent's mana value"
        ));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private ReckonersBargain(final ReckonersBargain card) {
        super(card);
    }

    @Override
    public ReckonersBargain copy() {
        return new ReckonersBargain(this);
    }
}

enum ReckonersBargainValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.castStream(sourceAbility.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
    }

    @Override
    public ReckonersBargainValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
