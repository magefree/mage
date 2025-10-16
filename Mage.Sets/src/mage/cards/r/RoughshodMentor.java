package mage.cards.r;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class RoughshodMentor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("Green creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public RoughshodMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Green creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    private RoughshodMentor(final RoughshodMentor card) {
        super(card);
    }

    @Override
    public RoughshodMentor copy() {
        return new RoughshodMentor(this);
    }
}
