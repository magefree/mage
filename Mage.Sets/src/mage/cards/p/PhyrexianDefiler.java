
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class PhyrexianDefiler extends CardImpl {

    public PhyrexianDefiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CARRIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {tap}, Sacrifice Phyrexian Defiler: Target creature gets -3/-3 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-3, -3, Duration.EndOfTurn), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PhyrexianDefiler(final PhyrexianDefiler card) {
        super(card);
    }

    @Override
    public PhyrexianDefiler copy() {
        return new PhyrexianDefiler(this);
    }
}
