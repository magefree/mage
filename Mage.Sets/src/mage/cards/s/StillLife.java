
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author LoneFox

 */
public final class StillLife extends CardImpl {

    public StillLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{G}");

        // {G}{G}: Still Life becomes a 4/3 Centaur creature until end of turn. It's still an enchantment.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(4, 3, "4/3 Centaur creature", SubType.CENTAUR),
                CardType.ENCHANTMENT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{G}{G}")
        ));
    }

    private StillLife(final StillLife card) {
        super(card);
    }

    @Override
    public StillLife copy() {
        return new StillLife(this);
    }
}
