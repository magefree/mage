
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CantBeRegeneratedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public final class HurrJackal extends CardImpl {

    public HurrJackal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.JACKAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature can't be regenerated this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeRegeneratedTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HurrJackal(final HurrJackal card) {
        super(card);
    }

    @Override
    public HurrJackal copy() {
        return new HurrJackal(this);
    }
}
