package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpelleaterWolverine extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(
            3, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
    );

    public SpelleaterWolverine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.WOLVERINE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Spelleater Wolverine has double strike as long as there are three or more instant and/or sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance()),
                condition, "{this} has double strike as long as there are " +
                "three or more instant and/or sorcery cards in your graveyard"
        )));
    }

    private SpelleaterWolverine(final SpelleaterWolverine card) {
        super(card);
    }

    @Override
    public SpelleaterWolverine copy() {
        return new SpelleaterWolverine(this);
    }
}
