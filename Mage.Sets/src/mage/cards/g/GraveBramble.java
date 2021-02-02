
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public final class GraveBramble extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("Zombies");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public GraveBramble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.PLANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(DefenderAbility.getInstance());
        // protection from Zombies
        this.addAbility(new ProtectionAbility(filter));
    }

    private GraveBramble(final GraveBramble card) {
        super(card);
    }

    @Override
    public GraveBramble copy() {
        return new GraveBramble(this);
    }
}
