

package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class FlamebornHellion extends CardImpl {

    public FlamebornHellion (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.HELLION);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private FlamebornHellion(final FlamebornHellion card) {
        super(card);
    }

    @Override
    public FlamebornHellion copy() {
        return new FlamebornHellion(this);
    }

}
