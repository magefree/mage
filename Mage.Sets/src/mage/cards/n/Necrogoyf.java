package mage.cards.n;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Necrogoyf extends CardImpl {

    private static final DynamicValue xValue = new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURES);

    public Necrogoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.LHURGOYF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Necrogoyf's power is equal to the number of creature cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(xValue)
        ));

        // At the beginning of each player's upkeep, that player discards a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.EACH_PLAYER, new DiscardTargetEffect(1),
                false
        ));

        // Madness {1}{B}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{B}{B}")));
    }

    private Necrogoyf(final Necrogoyf card) {
        super(card);
    }

    @Override
    public Necrogoyf copy() {
        return new Necrogoyf(this);
    }
}
