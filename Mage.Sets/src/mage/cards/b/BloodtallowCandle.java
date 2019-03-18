
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Rystan
 */
public final class BloodtallowCandle extends CardImpl {

    public BloodtallowCandle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {6}, {T}, Sacrifice Bloodtallow Candle: Target creature gets -5/-5 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(-5, -5, Duration.EndOfTurn),
                new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        
        this.addAbility(ability);
    }

    private BloodtallowCandle(final BloodtallowCandle card) {
        super(card);
    }

    @Override
    public BloodtallowCandle copy() {
        return new BloodtallowCandle(this);
    }
}
