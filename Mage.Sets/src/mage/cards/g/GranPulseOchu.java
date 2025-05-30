package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class GranPulseOchu extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT);

    public GranPulseOchu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {8}: Until end of turn, this creature gets +1/+1 for each permanent card in your graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("until end of turn, this creature gets +1/+1 for each permanent card in your graveyard"),
                new GenericManaCost(8)
        ).addHint(DescendCondition.getHint()));
    }

    private GranPulseOchu(final GranPulseOchu card) {
        super(card);
    }

    @Override
    public GranPulseOchu copy() {
        return new GranPulseOchu(this);
    }
}
