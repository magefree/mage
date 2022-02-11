package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DepravedHarvester extends CardImpl {

    public DepravedHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.nightCard = true;

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private DepravedHarvester(final DepravedHarvester card) {
        super(card);
    }

    @Override
    public DepravedHarvester copy() {
        return new DepravedHarvester(this);
    }
}
