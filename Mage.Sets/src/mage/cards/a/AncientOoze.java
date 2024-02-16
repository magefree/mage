package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.TotalPermanentsManaValue;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AncientOoze extends CardImpl {

    public AncientOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.OOZE);

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ancient Ooze's power and toughness are each equal to the total mana value of other creatures you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new SetBasePowerToughnessSourceEffect(
                new TotalPermanentsManaValue(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES)
            ).setText("{this}'s power and toughness are each equal to the total mana value of other creatures you control")
        ));
    }

    private AncientOoze(final AncientOoze card) {
        super(card);
    }

    @Override
    public AncientOoze copy() {
        return new AncientOoze(this);
    }
}
