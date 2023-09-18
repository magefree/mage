
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.SnakeToken;

/**
 *
 * @author Quercitron
 */
public final class SnakeBasket extends CardImpl {

    public SnakeBasket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {X}, Sacrifice Snake Basket: create X 1/1 green Snake creature tokens. Activate this ability only any time you could cast a sorcery.
        Effect effect = new CreateTokenEffect(new SnakeToken(), ManacostVariableValue.REGULAR);
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{X}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SnakeBasket(final SnakeBasket card) {
        super(card);
    }

    @Override
    public SnakeBasket copy() {
        return new SnakeBasket(this);
    }
}
