
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.WurmToken;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class RoarOfTheWurm extends CardImpl {

    public RoarOfTheWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{G}");


        // Create a 6/6 green Wurm creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WurmToken()));

        // Flashback {3}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{G}")));
    }

    private RoarOfTheWurm(final RoarOfTheWurm card) {
        super(card);
    }

    @Override
    public RoarOfTheWurm copy() {
        return new RoarOfTheWurm(this);
    }
}
