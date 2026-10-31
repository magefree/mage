package mage.cards.s;

import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.NewPlanetToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class StableWormhole extends CardImpl {

    public StableWormhole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you attack, create a tapped Planet land token named New Planet with "{T}: Add one mana of any color."
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new CreateTokenEffect(new NewPlanetToken(), 1, true), 1));
    }

    private StableWormhole(final StableWormhole card) {
        super(card);
    }

    @Override
    public StableWormhole copy() {
        return new StableWormhole(this);
    }
}
