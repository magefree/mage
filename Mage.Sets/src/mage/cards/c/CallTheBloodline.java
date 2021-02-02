
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.VampireKnightToken;

/**
 *
 * @author fireshoes
 */
public final class CallTheBloodline extends CardImpl {

    public CallTheBloodline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // {1}, Discard a card: Create a 1/1 black Vampire Knight token with lifelink. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new VampireKnightToken()), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private CallTheBloodline(final CallTheBloodline card) {
        super(card);
    }

    @Override
    public CallTheBloodline copy() {
        return new CallTheBloodline(this);
    }
}
