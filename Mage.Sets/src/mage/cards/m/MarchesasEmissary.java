
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DethroneAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class MarchesasEmissary extends CardImpl {

    public MarchesasEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Dethrone
        this.addAbility(new DethroneAbility());
    }

    private MarchesasEmissary(final MarchesasEmissary card) {
        super(card);
    }

    @Override
    public MarchesasEmissary copy() {
        return new MarchesasEmissary(this);
    }
}
