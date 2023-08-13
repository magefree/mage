
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class MemorySluice extends CardImpl {

    public MemorySluice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U/B}");

        // Target player puts the top four cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));

    }

    private MemorySluice(final MemorySluice card) {
        super(card);
    }

    @Override
    public MemorySluice copy() {
        return new MemorySluice(this);
    }
}
