package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CavernousMaw extends CardImpl {

    public CavernousMaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}: Cavernous Maw becomes a 3/3 Elemental creature until end of turn. It's still a Cave land. Activate only if the number of other Caves you control plus the number of Cave cards in your graveyard is three or greater.
        this.addAbility(new ConditionalActivatedAbility(
                new BecomesCreatureSourceEffect(
                        new CreatureToken(3, 3, "3/3 Elemental creature")
                                .withSubType(SubType.ELEMENTAL),
                        CardType.LAND, Duration.EndOfTurn
                ),
                new ManaCostsImpl<>("{2}"),
                new CavernousMawCondition()
        ).addHint(CavernousMawCondition.hint));
    }

    private CavernousMaw(final CavernousMaw card) {
        super(card);
    }

    @Override
    public CavernousMaw copy() {
        return new CavernousMaw(this);
    }
}

class CavernousMawCondition extends IntCompareCondition {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.CAVE, "other Caves you control");

    private static final FilterCard filterCard = new FilterCard("Cave cards");

    static {
        filter.add(AnotherPredicate.instance);
        filterCard.add(SubType.CAVE.getPredicate());
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(filter, null),
            new CardsInControllerGraveyardCount(filterCard)
    );

    static Hint hint = new ValueHint("Caves count", xValue);

    CavernousMawCondition() {
        super(ComparisonType.OR_GREATER, 3);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return xValue.calculate(game, source, null);
    }

    @Override
    public String toString() {
        return "if the number of other Caves you control plus the number of Cave cards in your graveyard is three or greater";
    }
}
