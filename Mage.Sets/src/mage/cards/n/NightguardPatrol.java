

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class NightguardPatrol extends CardImpl {

    public NightguardPatrol (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
    }

    private NightguardPatrol(final NightguardPatrol card) {
        super(card);
    }

    @Override
    public NightguardPatrol copy() {
        return new NightguardPatrol(this);
    }

}
