package mage.cards.g;

import mage.MageInt;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreatForestDruid extends CardImpl {

    public GreatForestDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private GreatForestDruid(final GreatForestDruid card) {
        super(card);
    }

    @Override
    public GreatForestDruid copy() {
        return new GreatForestDruid(this);
    }
}
