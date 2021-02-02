
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class VampireCutthroat extends CardImpl {

    public VampireCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Skulk
        this.addAbility(new SkulkAbility());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private VampireCutthroat(final VampireCutthroat card) {
        super(card);
    }

    @Override
    public VampireCutthroat copy() {
        return new VampireCutthroat(this);
    }
}
