
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DinosaurToken;

/**
 *
 * @author TheElk801
 */
public final class RaptorHatchling extends CardImpl {

    public RaptorHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Enrage - Whenever Raptor Hatchling is dealt damage, create a 3/3 green Dinosaur creature token with trample.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new CreateTokenEffect(new DinosaurToken()), false, true);
        this.addAbility(ability);    }

    public RaptorHatchling(final RaptorHatchling card) {
        super(card);
    }

    @Override
    public RaptorHatchling copy() {
        return new RaptorHatchling(this);
    }
}
