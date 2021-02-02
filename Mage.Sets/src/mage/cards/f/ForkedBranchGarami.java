
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ForkedBranchGarami extends CardImpl {

    public ForkedBranchGarami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Soulshift 4, soulshift 4 (When this creature dies, you may return up to two target Spirit cards with converted mana cost 4 or less from your graveyard to your hand.)
        this.addAbility(new SoulshiftAbility(4));
        this.addAbility(new SoulshiftAbility(4));
    }

    private ForkedBranchGarami(final ForkedBranchGarami card) {
        super(card);
    }

    @Override
    public ForkedBranchGarami copy() {
        return new ForkedBranchGarami(this);
    }
}
