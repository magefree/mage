
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class RavensRunDragoon extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RavensRunDragoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G/W}{G/W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Raven's Run Dragoon can't be blocked by black creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
        
    }

    private RavensRunDragoon(final RavensRunDragoon card) {
        super(card);
    }

    @Override
    public RavensRunDragoon copy() {
        return new RavensRunDragoon(this);
    }
}
