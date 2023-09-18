
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
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
 * @author LoneFox
 */
public final class SmokespewInvoker extends CardImpl {

    public SmokespewInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {7}{B}: Target creature gets -3/-3 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-3, -3, Duration.EndOfTurn),
            new ManaCostsImpl<>("{7}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SmokespewInvoker(final SmokespewInvoker card) {
        super(card);
    }

    @Override
    public SmokespewInvoker copy() {
        return new SmokespewInvoker(this);
    }
}
