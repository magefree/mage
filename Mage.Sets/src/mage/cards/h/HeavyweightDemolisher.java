package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeavyweightDemolisher extends CardImpl {

    public HeavyweightDemolisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your upkeep, tap Heavyweight Demolisher unless you pay {3}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TapSourceUnlessPaysEffect(new GenericManaCost(3)),
                TargetController.YOU, false
        ));

        // Unearth {6}{R}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{6}{R}{R}")));
    }

    private HeavyweightDemolisher(final HeavyweightDemolisher card) {
        super(card);
    }

    @Override
    public HeavyweightDemolisher copy() {
        return new HeavyweightDemolisher(this);
    }
}
