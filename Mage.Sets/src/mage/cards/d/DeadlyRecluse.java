

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

        // Reach (This creature can block creatures with flying.)
        this.addAbility(ReachAbility.getInstance());

        // Deathtouch (Any amount of damage this deals to a creature is enough to destroy it.)
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private DeadlyRecluse(final DeadlyRecluse card) {
        super(card);
    }

    @Override
    public DeadlyRecluse copy() {
        return new DeadlyRecluse(this);
    }

}
