package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedTigerMechan extends CardImpl {

    public RedTigerMechan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Warp {1}{R}
        this.addAbility(new WarpAbility(this, "{1}{R}"));
    }

    private RedTigerMechan(final RedTigerMechan card) {
        super(card);
    }

    @Override
    public RedTigerMechan copy() {
        return new RedTigerMechan(this);
    }
}
