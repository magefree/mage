
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Quercitron
 */
public final class MysticCompass extends CardImpl {

    public MysticCompass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {1}, {tap}: Target land becomes the basic land type of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesBasicLandTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private MysticCompass(final MysticCompass card) {
        super(card);
    }

    @Override
    public MysticCompass copy() {
        return new MysticCompass(this);
    }
}
