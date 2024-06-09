
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author Loki
 */
public final class VerdantForce extends CardImpl {

    public VerdantForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of each upkeep, create a 1/1 green Saproling creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new SaprolingToken()), TargetController.EACH_PLAYER, false));
    }

    private VerdantForce(final VerdantForce card) {
        super(card);
    }

    @Override
    public VerdantForce copy() {
        return new VerdantForce(this);
    }
}
