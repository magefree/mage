
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class BrothersOfFire extends CardImpl {

    public BrothersOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN, SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}{R}: Brothers of Fire deals 1 damage to any target and 1 damage to you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}{R}"));
        Effect effect = new DamageControllerEffect(1);
        effect.setText("and 1 damage to you");
        ability.addEffect(effect);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BrothersOfFire(final BrothersOfFire card) {
        super(card);
    }

    @Override
    public BrothersOfFire copy() {
        return new BrothersOfFire(this);
    }
}
