package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaloHopper extends CardImpl {

    public HaloHopper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.FROG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Convoke
        this.addAbility(new ConvokeAbility());
    }

    private HaloHopper(final HaloHopper card) {
        super(card);
    }

    @Override
    public HaloHopper copy() {
        return new HaloHopper(this);
    }
}
