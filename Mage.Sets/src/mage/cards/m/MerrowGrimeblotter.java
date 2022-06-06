
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
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
 * @author jeffwadsworth
 */
public final class MerrowGrimeblotter extends CardImpl {

    public MerrowGrimeblotter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U/B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{UB}, {untap}: Target creature gets -2/-0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U/B}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }

    private MerrowGrimeblotter(final MerrowGrimeblotter card) {
        super(card);
    }

    @Override
    public MerrowGrimeblotter copy() {
        return new MerrowGrimeblotter(this);
    }
}
