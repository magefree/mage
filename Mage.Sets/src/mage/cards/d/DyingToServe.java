package mage.cards.d;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DyingToServe extends CardImpl {

    public DyingToServe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever you discard one or more cards, create a tapped 2/2 black Zombie creature token. This ability triggers only once each turn.
        this.addAbility(new DiscardCardControllerTriggeredAbility(new CreateTokenEffect(
                new ZombieToken(), 1, true, false), false
        ).setTriggerPhrase("Whenever you discard one or more cards, ").setTriggersOnce(true));
    }

    private DyingToServe(final DyingToServe card) {
        super(card);
    }

    @Override
    public DyingToServe copy() {
        return new DyingToServe(this);
    }
}
