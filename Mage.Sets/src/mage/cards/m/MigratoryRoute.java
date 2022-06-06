
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BirdToken;

/**
 *
 * @author fireshoes
 */
public final class MigratoryRoute extends CardImpl {

    public MigratoryRoute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{U}");


        // Create four 1/1 white Bird creature tokens with flying.
        getSpellAbility().addEffect(new CreateTokenEffect(new BirdToken(), 4));

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private MigratoryRoute(final MigratoryRoute card) {
        super(card);
    }

    @Override
    public MigratoryRoute copy() {
        return new MigratoryRoute(this);
    }
}
