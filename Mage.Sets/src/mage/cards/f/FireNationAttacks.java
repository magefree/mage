package mage.cards.f;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierFirebendingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationAttacks extends CardImpl {

    public FireNationAttacks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Create two 2/2 red Soldier creature tokens with firebending 1.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierFirebendingToken(), 2));

        // Flashback {8}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{8}{R}")));
    }

    private FireNationAttacks(final FireNationAttacks card) {
        super(card);
    }

    @Override
    public FireNationAttacks copy() {
        return new FireNationAttacks(this);
    }
}
