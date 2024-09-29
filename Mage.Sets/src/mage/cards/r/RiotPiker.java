

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */


public final class RiotPiker extends CardImpl {

    public RiotPiker (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Riot Piker attacks each turn if able
        this.addAbility(new AttacksEachCombatStaticAbility());

    }

    private RiotPiker(final RiotPiker card) {
        super(card);
    }

    @Override
    public RiotPiker copy() {
        return new RiotPiker(this);
    }

}
