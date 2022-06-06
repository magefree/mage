
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class NightscapeMaster extends CardImpl {

    public NightscapeMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}{U}, {tap}: Return target creature to its owner's hand.
        Ability returnAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{U}{U}"));
        returnAbility.addTarget(new TargetCreaturePermanent());
        returnAbility.addCost(new TapSourceCost());
        this.addAbility(returnAbility);
        
        // {R}{R}, {tap}: Nightscape Master deals 2 damage to target creature.
        Ability damageAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{R}{R}"));
        damageAbility.addTarget(new TargetCreaturePermanent());
        damageAbility.addCost(new TapSourceCost());
        this.addAbility(damageAbility);
    }

    private NightscapeMaster(final NightscapeMaster card) {
        super(card);
    }

    @Override
    public NightscapeMaster copy() {
        return new NightscapeMaster(this);
    }
}
