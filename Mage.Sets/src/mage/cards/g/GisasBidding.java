
package mage.cards.g;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author anonymous
 */
public final class GisasBidding extends CardImpl {

    public GisasBidding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Create two 2/2 black Zombie creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken(), 2));

        // Madness {2}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{2}{B}")));
    }

    private GisasBidding(final GisasBidding card) {
        super(card);
    }

    @Override
    public GisasBidding copy() {
        return new GisasBidding(this);
    }
}
