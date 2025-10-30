package mage.cards.b;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class BloodmarkMentor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("Red creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public BloodmarkMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Red creatures you control have first strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    private BloodmarkMentor(final BloodmarkMentor card) {
        super(card);
    }

    @Override
    public BloodmarkMentor copy() {
        return new BloodmarkMentor(this);
    }
}
