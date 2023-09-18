package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoltChargedBerserker extends CardImpl {

    public VoltChargedBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.nightCard = true;

        // Volt-Charged Berserker can't block.
        this.addAbility(new CantBlockAbility());
    }

    private VoltChargedBerserker(final VoltChargedBerserker card) {
        super(card);
    }

    @Override
    public VoltChargedBerserker copy() {
        return new VoltChargedBerserker(this);
    }
}
