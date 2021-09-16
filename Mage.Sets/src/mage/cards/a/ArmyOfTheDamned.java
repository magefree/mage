
package mage.cards.a;

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
 * @author nantuko
 */
public final class ArmyOfTheDamned extends CardImpl {

    public ArmyOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}{B}");

        // Create thirteen 2/2 black Zombie creature tokens tapped.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken(), 13, true, false));

        // Flashback {7}{B}{B}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{7}{B}{B}{B}")));
    }

    private ArmyOfTheDamned(final ArmyOfTheDamned card) {
        super(card);
    }

    @Override
    public ArmyOfTheDamned copy() {
        return new ArmyOfTheDamned(this);
    }
}
