
package mage.cards.z;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.ZendikarsRoilElementalToken;

/**
 *
 * @author LevelX2
 */
public final class ZendikarsRoil extends CardImpl {

    public ZendikarsRoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // Whenever a land enters the battlefield under your control, create a 2/2 green Elemental creature token.
        Effect effect = new CreateTokenEffect(new ZendikarsRoilElementalToken());
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(effect, new FilterLandPermanent("a land")));
    }

    public ZendikarsRoil(final ZendikarsRoil card) {
        super(card);
    }

    @Override
    public ZendikarsRoil copy() {
        return new ZendikarsRoil(this);
    }
}
