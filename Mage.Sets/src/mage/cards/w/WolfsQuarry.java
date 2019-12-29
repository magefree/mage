package mage.cards.w;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.WolfsQuarryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WolfsQuarry extends CardImpl {

    public WolfsQuarry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Create three 1/1 green Boar creature tokens with "When this creature dies, create a Food token."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WolfsQuarryToken(), 3));
    }

    private WolfsQuarry(final WolfsQuarry card) {
        super(card);
    }

    @Override
    public WolfsQuarry copy() {
        return new WolfsQuarry(this);
    }
}
