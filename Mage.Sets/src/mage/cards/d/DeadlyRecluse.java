

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DeadlyRecluse extends CardImpl {

    public DeadlyRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
    this.toughness = new MageInt(2);
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());
    }

    public DeadlyRecluse(final DeadlyRecluse card) {
        super(card);
    }

    @Override
    public DeadlyRecluse copy() {
        return new DeadlyRecluse(this);
    }

}
