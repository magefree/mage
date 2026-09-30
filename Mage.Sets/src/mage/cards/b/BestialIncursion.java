package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Beast44TrampleToken;

/**
 *
 * @author muz
 */
public final class BestialIncursion extends CardImpl {

    public BestialIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Create a 4/4 green Beast creature token with trample.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Beast44TrampleToken()));

        // Flashback {5}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{G}")));
    }

    private BestialIncursion(final BestialIncursion card) {
        super(card);
    }

    @Override
    public BestialIncursion copy() {
        return new BestialIncursion(this);
    }
}
