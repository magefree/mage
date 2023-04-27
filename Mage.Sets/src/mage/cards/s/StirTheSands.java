
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author fireshoes
 */
public final class StirTheSands extends CardImpl {

    public StirTheSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Create three 2/2 black Zombie creature tokens.
        getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken(), 3));

        // Cycling {3}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}{B}")));

        // When you cycle Stir the Sands, create a 2/2 black Zombie creature token.
        this.addAbility(new CycleTriggeredAbility(new CreateTokenEffect(new ZombieToken())));
    }

    private StirTheSands(final StirTheSands card) {
        super(card);
    }

    @Override
    public StirTheSands copy() {
        return new StirTheSands(this);
    }
}
