package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessPlusOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SoulsOfTheLost extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENTS);

    public SoulsOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // As an additional cost to cast this spell, discard a card or sacrifice a permanent.
        this.getSpellAbility().addCost(new OrCost("discard a card or sacrifice a permanent",
                new DiscardCardCost(),
                new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT))
        ));

        // Fathomless descent -- Souls of the Lost's power is equal to the number of permanent cards in your graveyard and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessPlusOneSourceEffect(xValue))
                .setAbilityWord(AbilityWord.FATHOMLESS_DESCENT).addHint(DescendCondition.getHint()));
    }

    private SoulsOfTheLost(final SoulsOfTheLost card) {
        super(card);
    }

    @Override
    public SoulsOfTheLost copy() {
        return new SoulsOfTheLost(this);
    }
}
