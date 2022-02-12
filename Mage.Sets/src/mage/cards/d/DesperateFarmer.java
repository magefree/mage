package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesperateFarmer extends CardImpl {

    public DesperateFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.d.DepravedHarvester.class;

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When another creature you control dies, transform Desperate Farmer.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesCreatureTriggeredAbility(
                new TransformSourceEffect(), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).setTriggerPhrase("When another creature you control dies, "));
    }

    private DesperateFarmer(final DesperateFarmer card) {
        super(card);
    }

    @Override
    public DesperateFarmer copy() {
        return new DesperateFarmer(this);
    }
}
