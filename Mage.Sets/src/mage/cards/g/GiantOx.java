package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.CrewWithToughnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author varaghar
 */
public final class GiantOx extends CardImpl {

    public GiantOx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.OX);

        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Giant Ox crews Vehicles using its toughness rather than its power.
        this.addAbility(CrewWithToughnessAbility.getInstance());
    }

    private GiantOx(final GiantOx card) {
        super(card);
    }

    @Override
    public GiantOx copy() {
        return new GiantOx(this);
    }
}
