package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class Gobland extends CardImpl {

    public Gobland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND, CardType.CREATURE}, "");
        this.subtype.add(SubType.MOUNTAIN, SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.color.setRed(true);

        // (Gobland isn't a spell, it's affected by summoning sickness, and it has "{T}: Add {R}.")
        this.addAbility(new SimpleStaticAbility(new InfoEffect(
                "<i>({this} isn't a spell, it's affected by summoning sickness, and it has \"{T}: Add {R}.\")</i>"
        )));

        // Add {R}
        this.addAbility(new RedManaAbility());

        // Gobland can't block.
        this.addAbility(new CantBlockAbility());
    }

    private Gobland(final Gobland card) {
        super(card);
    }

    @Override
    public Gobland copy() {
        return new Gobland(this);
    }
}
