
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class SunscapeMaster extends CardImpl {

    public SunscapeMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{G}, {tap}: Creatures you control get +2/+2 until end of turn.
        Effect effect1 = new BoostControlledEffect(2, 2, Duration.EndOfTurn);
        effect1.setText("Creatures you control get +2/+2 until end of turn");
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect1, new ManaCostsImpl<>("{G}{G}"));
        ability1.addCost(new TapSourceCost());
        this.addAbility(ability1);
        
        // {U}{U}, {tap}: Return target creature to its owner's hand.
        Effect effect2 = new ReturnToHandTargetEffect();
        effect2.setText("Return target creature to its owner's hand.");
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, new ManaCostsImpl<>("{U}{U}"));
        ability2.addTarget(new TargetCreaturePermanent());
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
    }

    private SunscapeMaster(final SunscapeMaster card) {
        super(card);
    }

    @Override
    public SunscapeMaster copy() {
        return new SunscapeMaster(this);
    }
}
