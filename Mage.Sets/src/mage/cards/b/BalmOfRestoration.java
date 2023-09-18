
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class BalmOfRestoration extends CardImpl {

    public BalmOfRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {1}, {tap}, Sacrifice Balm of Restoration: Choose one - You gain 2 life;
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        
        // or prevent the next 2 damage that would be dealt to any target this turn.
        Mode mode = new Mode(new PreventDamageToTargetEffect(Duration.EndOfTurn, 2));
        mode.addTarget(new TargetAnyTarget());
        ability.addMode(mode);
        
        this.addAbility(ability);
    }

    private BalmOfRestoration(final BalmOfRestoration card) {
        super(card);
    }

    @Override
    public BalmOfRestoration copy() {
        return new BalmOfRestoration(this);
    }
}
