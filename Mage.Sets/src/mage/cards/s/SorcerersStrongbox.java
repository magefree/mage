
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SorcerersStrongbox extends CardImpl {

    public SorcerersStrongbox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {T} : Flip a coin. If you win the flip, sacrifice Sorcerer's Strongbox and draw three cards.
        FlipCoinEffect flipCoinEffect = new FlipCoinEffect(new SacrificeSourceEffect());
        Effect effect = new DrawCardSourceControllerEffect(3);
        effect.setText("and draw three cards");
        flipCoinEffect.addEffectWon(effect);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, flipCoinEffect, new TapSourceCost());
        ability.addCost(new GenericManaCost(2));
        this.addAbility(ability);
    }

    private SorcerersStrongbox(final SorcerersStrongbox card) {
        super(card);
    }

    @Override
    public SorcerersStrongbox copy() {
        return new SorcerersStrongbox(this);
    }

}
