
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Wehk
 */
public final class MageRingBully extends CardImpl {

    public MageRingBully(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());
        
         // Mage-Ring Bully attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private MageRingBully(final MageRingBully card) {
        super(card);
    }

    @Override
    public MageRingBully copy() {
        return new MageRingBully(this);
    }
}
