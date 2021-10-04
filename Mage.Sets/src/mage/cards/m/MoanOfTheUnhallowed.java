
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author nantuko
 */
public final class MoanOfTheUnhallowed extends CardImpl {

    public MoanOfTheUnhallowed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Create two 2/2 black Zombie creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken(), 2));

        // Flashback {5}{B}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{B}{B}")));
    }

    private MoanOfTheUnhallowed(final MoanOfTheUnhallowed card) {
        super(card);
    }

    @Override
    public MoanOfTheUnhallowed copy() {
        return new MoanOfTheUnhallowed(this);
    }
}
