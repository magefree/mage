
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class CorrosiveMentor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public CorrosiveMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Black creatures you control have wither.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(WitherAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    public CorrosiveMentor(final CorrosiveMentor card) {
        super(card);
    }

    @Override
    public CorrosiveMentor copy() {
        return new CorrosiveMentor(this);
    }
}
