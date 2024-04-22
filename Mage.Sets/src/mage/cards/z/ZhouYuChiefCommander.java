
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class ZhouYuChiefCommander extends CardImpl {

    public ZhouYuChiefCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Zhou Yu, Chief Commander can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND,"an Island"))));
    }

    private ZhouYuChiefCommander(final ZhouYuChiefCommander card) {
        super(card);
    }

    @Override
    public ZhouYuChiefCommander copy() {
        return new ZhouYuChiefCommander(this);
    }
}
