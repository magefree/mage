package mage.cards.z;

import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZendikarsRoilElementalToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ZendikarsRoil extends CardImpl {

    public ZendikarsRoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // Whenever a land enters the battlefield under your control, create a 2/2 green Elemental creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new ZendikarsRoilElementalToken())));
    }

    private ZendikarsRoil(final ZendikarsRoil card) {
        super(card);
    }

    @Override
    public ZendikarsRoil copy() {
        return new ZendikarsRoil(this);
    }
}
