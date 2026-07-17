package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.keyword.IncrementAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TopiaryLecturer extends CardImpl {

    public TopiaryLecturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Increment
        this.addAbility(new IncrementAbility());

        // {T}: Add an amount of {G} equal to this creature's power.
        this.addAbility(new DynamicManaAbility(
            Mana.GreenMana(1), SourcePermanentPowerValue.NOT_NEGATIVE, "Add an amount of {G} equal to {this}'s power."
        ));
    }

    private TopiaryLecturer(final TopiaryLecturer card) {
        super(card);
    }

    @Override
    public TopiaryLecturer copy() {
        return new TopiaryLecturer(this);
    }
}
