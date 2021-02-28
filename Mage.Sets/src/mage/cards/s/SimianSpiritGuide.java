package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.ExileSourceFromHandCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SimianSpiritGuide extends CardImpl {

    public SimianSpiritGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exile Simian Spirit Guide from your hand: Add {R}.
        this.addAbility(new SimpleManaAbility(Zone.HAND, Mana.RedMana(1), new ExileSourceFromHandCost()));
    }

    private SimianSpiritGuide(final SimianSpiritGuide card) {
        super(card);
    }

    @Override
    public SimianSpiritGuide copy() {
        return new SimianSpiritGuide(this);
    }
}
