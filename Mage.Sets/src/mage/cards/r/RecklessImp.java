
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.DashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RecklessImp extends CardImpl {

    public RecklessImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Reckless Imp can't block.
        this.addAbility(new CantBlockAbility());
                
        // Dash {1}{B}
        this.addAbility(new DashAbility("{1}{B}"));
    }

    private RecklessImp(final RecklessImp card) {
        super(card);
    }

    @Override
    public RecklessImp copy() {
        return new RecklessImp(this);
    }
}
