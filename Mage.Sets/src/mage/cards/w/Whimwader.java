
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth

 */
public final class Whimwader extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a blue permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public Whimwader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Whimwader can't attack unless defending player controls a blue permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(filter)));
        
    }

    private Whimwader(final Whimwader card) {
        super(card);
    }

    @Override
    public Whimwader copy() {
        return new Whimwader(this);
    }
}
