
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class DutyBoundDead extends CardImpl {

    public DutyBoundDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Exalted
        this.addAbility(new ExaltedAbility());
        // {3}{B}: Regenerate Duty-Bound Dead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{3}{B}")));
    }

    private DutyBoundDead(final DutyBoundDead card) {
        super(card);
    }

    @Override
    public DutyBoundDead copy() {
        return new DutyBoundDead(this);
    }
}
