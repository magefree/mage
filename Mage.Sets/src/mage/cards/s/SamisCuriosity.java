package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamisCuriosity extends CardImpl {

    public SamisCuriosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // You gain 2 life. Create a Lander token.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new LanderToken()));
    }

    private SamisCuriosity(final SamisCuriosity card) {
        super(card);
    }

    @Override
    public SamisCuriosity copy() {
        return new SamisCuriosity(this);
    }
}
