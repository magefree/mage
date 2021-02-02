

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BerserkersOfBloodRidge  extends CardImpl {

    public BerserkersOfBloodRidge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");

        this.subtype.add(SubType.HUMAN, SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private BerserkersOfBloodRidge(final BerserkersOfBloodRidge card) {
        super(card);
    }

    @Override
    public BerserkersOfBloodRidge copy() {
        return new BerserkersOfBloodRidge(this);
    }

}
