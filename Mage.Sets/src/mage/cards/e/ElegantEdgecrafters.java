package mage.cards.e;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ElegantEdgecrafters extends CardImpl {

    public ElegantEdgecrafters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Elegant Edgecrafters can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Fabricate 2
        this.addAbility(new FabricateAbility(2));
    }

    private ElegantEdgecrafters(final ElegantEdgecrafters card) {
        super(card);
    }

    @Override
    public ElegantEdgecrafters copy() {
        return new ElegantEdgecrafters(this);
    }
}
