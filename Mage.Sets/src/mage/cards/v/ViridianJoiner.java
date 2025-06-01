package mage.cards.v;

import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class ViridianJoiner extends CardImpl {

    public ViridianJoiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Add an amount of {G} equal to Viridian Joiner's power.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1),
                SourcePermanentPowerValue.NOT_NEGATIVE,
                "Add an amount of {G} equal to {this}'s power"
        ));
    }

    private ViridianJoiner(final ViridianJoiner card) {
        super(card);
    }

    @Override
    public ViridianJoiner copy() {
        return new ViridianJoiner(this);
    }
}
