package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.DjinnToken;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class BottleOfSuleiman extends CardImpl {

    public BottleOfSuleiman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, Sacrifice Bottle of Suleiman: Flip a coin. If you lose the flip, Bottle of Suleiman deals 5 damage to you. If you win the flip, create a 5/5 colorless Djinn artifact creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FlipCoinEffect(
                new CreateTokenEffect(new DjinnToken()),
                new DamageControllerEffect(5)
        ), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BottleOfSuleiman(final BottleOfSuleiman card) {
        super(card);
    }

    @Override
    public BottleOfSuleiman copy() {
        return new BottleOfSuleiman(this);
    }
}
