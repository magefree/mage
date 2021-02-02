
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author fireshoes
 */
public final class Whirlermaker extends CardImpl {

    public Whirlermaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {4}, {T}: Create a 1/1 colorless Thopter artifact creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterColorlessToken()), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Whirlermaker(final Whirlermaker card) {
        super(card);
    }

    @Override
    public Whirlermaker copy() {
        return new Whirlermaker(this);
    }
}
