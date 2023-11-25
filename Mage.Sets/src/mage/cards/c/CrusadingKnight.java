
package mage.cards.c;

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
 * @author anonymous
 */
public final class CrusadingKnight extends CardImpl {

    private static final FilterLandPermanent swampFilter = new FilterLandPermanent("Swamp your opponents control");
    static {
      swampFilter.add(SubType.SWAMP.getPredicate());
      swampFilter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public CrusadingKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from black
      this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // Crusading Knight gets +1/+1 for each Swamp your opponents control.
      PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(swampFilter, 1);
      SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield));
      this.addAbility(ability);
    }

    private CrusadingKnight(final CrusadingKnight card) {
        super(card);
    }

    @Override
    public CrusadingKnight copy() {
        return new CrusadingKnight(this);
    }
}
