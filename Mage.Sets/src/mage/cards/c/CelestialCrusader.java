
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class CelestialCrusader extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public CelestialCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);


        // Flash (You may cast this spell any time you could cast an instant.)
        this.addAbility(FlashAbility.getInstance());

        // Split second (As long as this spell is on the stack, players can't cast spells or activate abilities that aren't mana abilities.)
        this.addAbility(new SplitSecondAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other white creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    private CelestialCrusader(final CelestialCrusader card) {
        super(card);
    }

    @Override
    public CelestialCrusader copy() {
        return new CelestialCrusader(this);
    }
}
