
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class Whetwheel extends CardImpl {

    public Whetwheel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {X}{X}, {tap}: Target player puts the top X cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(
            ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Morph {3}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}")));
    }

    private Whetwheel(final Whetwheel card) {
        super(card);
    }

    @Override
    public Whetwheel copy() {
        return new Whetwheel(this);
    }
}
