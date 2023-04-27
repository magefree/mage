package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessBarbarian extends CardImpl {

    public RecklessBarbarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Reckless Barbarian: Add {R}{R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new SacrificeSourceCost()));
    }

    private RecklessBarbarian(final RecklessBarbarian card) {
        super(card);
    }

    @Override
    public RecklessBarbarian copy() {
        return new RecklessBarbarian(this);
    }
}
