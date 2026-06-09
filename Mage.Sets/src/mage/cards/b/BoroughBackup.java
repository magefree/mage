package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Hero32Token;

/**
 *
 * @author muz
 */
public final class BoroughBackup extends CardImpl {

    public BoroughBackup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Create two 3/2 white Hero creature tokens with vigilance.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Hero32Token(), 2));

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private BoroughBackup(final BoroughBackup card) {
        super(card);
    }

    @Override
    public BoroughBackup copy() {
        return new BoroughBackup(this);
    }
}
