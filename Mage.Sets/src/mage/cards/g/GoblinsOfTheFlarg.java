
package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GoblinsOfTheFlarg extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a Dwarf");

    static {
        filter.add(SubType.DWARF.getPredicate());
    }

    public GoblinsOfTheFlarg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Mountainwalk
        this.addAbility(new MountainwalkAbility());

        // When you control a Dwarf, sacrifice Goblins of the Flarg.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                filter,
                new SacrificeSourceEffect()));
    }

    private GoblinsOfTheFlarg(final GoblinsOfTheFlarg card) {
        super(card);
    }

    @Override
    public GoblinsOfTheFlarg copy() {
        return new GoblinsOfTheFlarg(this);
    }
}
