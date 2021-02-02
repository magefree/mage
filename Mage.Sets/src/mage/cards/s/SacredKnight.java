
package mage.cards.s;

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
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class SacredKnight extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black and/or red creatures");
    
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK),
                (new ColorPredicate(ObjectColor.RED))));
    }

    public SacredKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Sacred Knight can't be blocked by black and/or red creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private SacredKnight(final SacredKnight card) {
        super(card);
    }

    @Override
    public SacredKnight copy() {
        return new SacredKnight(this);
    }
}
