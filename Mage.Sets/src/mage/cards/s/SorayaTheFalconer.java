
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author L_J
 */
public final class SorayaTheFalconer extends CardImpl {

    public SorayaTheFalconer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bird creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield,
                new FilterCreaturePermanent(SubType.BIRD, "Bird creatures"), false)));

        // {1}{W}: Target Bird creature gains banding until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(BandingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent(SubType.BIRD, "Bird creature")));
        this.addAbility(ability);

    }

    private SorayaTheFalconer(final SorayaTheFalconer card) {
        super(card);
    }

    @Override
    public SorayaTheFalconer copy() {
        return new SorayaTheFalconer(this);
    }
}
