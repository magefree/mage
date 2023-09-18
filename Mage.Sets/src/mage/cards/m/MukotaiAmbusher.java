package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MukotaiAmbusher extends CardImpl {

    public MukotaiAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ninjutsu {1}{B}
        this.addAbility(new NinjutsuAbility("{1}{B}"));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private MukotaiAmbusher(final MukotaiAmbusher card) {
        super(card);
    }

    @Override
    public MukotaiAmbusher copy() {
        return new MukotaiAmbusher(this);
    }
}
