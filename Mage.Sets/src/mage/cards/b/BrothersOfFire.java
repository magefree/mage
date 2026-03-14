package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetAndYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

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
        Ability ability = new SimpleActivatedAbility(new DamageTargetAndYouEffect(1), new ManaCostsImpl<>("{1}{R}{R}"));
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
