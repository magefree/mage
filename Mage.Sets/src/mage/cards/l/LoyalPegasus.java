
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class LoyalPegasus extends CardImpl {

    public LoyalPegasus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.PEGASUS);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Loyal Pegasus can't attack or block alone.
        this.addAbility(new CantAttackAloneAbility());
        this.addAbility(CantBlockAloneAbility.getInstance());
    }

    private LoyalPegasus(final LoyalPegasus card) {
        super(card);
    }

    @Override
    public LoyalPegasus copy() {
        return new LoyalPegasus(this);
    }
}
