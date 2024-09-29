
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 * @author nantuko
 */
public final class DreamTwist extends CardImpl {

    public DreamTwist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Target player puts the top three cards of their library into their graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3));

        // Flashback {1}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}")));
    }

    private DreamTwist(final DreamTwist card) {
        super(card);
    }

    @Override
    public DreamTwist copy() {
        return new DreamTwist(this);
    }
}
