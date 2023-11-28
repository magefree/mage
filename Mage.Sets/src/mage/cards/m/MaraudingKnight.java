
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class MaraudingKnight extends CardImpl {

    private static final FilterLandPermanent plainsFilter = new FilterLandPermanent("Plains your opponents control");
    static {
      plainsFilter.add(SubType.PLAINS.getPredicate());
      plainsFilter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public MaraudingKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));

        // Marauding Knight gets +1/+1 for each Plains your opponents control.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(plainsFilter, 1);
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private MaraudingKnight(final MaraudingKnight card) {
        super(card);
    }

    @Override
    public MaraudingKnight copy() {
        return new MaraudingKnight(this);
    }
}
