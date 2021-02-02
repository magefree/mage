
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DethroneAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GrenzosCutthroat extends CardImpl {

    public GrenzosCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Dethrone
        this.addAbility(new DethroneAbility());
    }

    private GrenzosCutthroat(final GrenzosCutthroat card) {
        super(card);
    }

    @Override
    public GrenzosCutthroat copy() {
        return new GrenzosCutthroat(this);
    }
}
