package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReclusiveTaxidermist extends CardImpl {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURE);
    private static final Hint hint = new ValueHint(
            "Creature cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
    );

    public ReclusiveTaxidermist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Reclusive Taxidermist gets +3/+2 as long as there are four or more creature cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 2, Duration.WhileOnBattlefield),
                condition, "{this} gets +3/+2 as long as there are four or more creature cards in your graveyard"
        )).addHint(hint));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private ReclusiveTaxidermist(final ReclusiveTaxidermist card) {
        super(card);
    }

    @Override
    public ReclusiveTaxidermist copy() {
        return new ReclusiveTaxidermist(this);
    }
}
