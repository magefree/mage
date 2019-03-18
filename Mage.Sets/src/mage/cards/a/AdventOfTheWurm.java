

package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.WurmToken2;

/**
 *
 * @author LevelX2
 */


public final class AdventOfTheWurm extends CardImpl {

    public AdventOfTheWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}{W}");

        // Create a 5/5 green Wurm creature token with trample.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WurmToken2()));
    }

    public AdventOfTheWurm(final AdventOfTheWurm card) {
        super(card);
    }

    @Override
    public AdventOfTheWurm copy() {
        return new AdventOfTheWurm(this);
    }
}
