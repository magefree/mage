
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author FenrisulfrX
 */
public final class PhyrexianColossus extends CardImpl {

    public PhyrexianColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // {this} doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        // Pay 8 life: Untap {this}.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new UntapSourceEffect(), new PayLifeCost(8)));
        // {this} can't be blocked except by three or more creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByOneEffect(3)));
        
    }

    private PhyrexianColossus(final PhyrexianColossus card) {
        super(card);
    }

    @Override
    public PhyrexianColossus copy() {
        return new PhyrexianColossus(this);
    }
}
