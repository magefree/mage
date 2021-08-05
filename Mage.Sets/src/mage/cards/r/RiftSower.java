package mage.cards.r;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiftSower extends CardImpl {

    public RiftSower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Suspend 2—{G}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl<>("{G}"), this));
    }

    private RiftSower(final RiftSower card) {
        super(card);
    }

    @Override
    public RiftSower copy() {
        return new RiftSower(this);
    }
}
