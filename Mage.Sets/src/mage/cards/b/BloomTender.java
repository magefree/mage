package mage.cards.b;

import mage.MageInt;
import mage.abilities.mana.AddEachControlledColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BloomTender extends CardImpl {

    public BloomTender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF, SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: For each color among permanents you control, add one mana of that color.
        this.addAbility(new AddEachControlledColorManaAbility());
    }

    private BloomTender(final BloomTender card) {
        super(card);
    }

    @Override
    public BloomTender copy() {
        return new BloomTender(this);
    }
}
