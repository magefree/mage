
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class HarshScrutiny extends CardImpl {

    public HarshScrutiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Target opponent reveals their hand. You choose a creature card from it. That player discards that card. Scry 1.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(new FilterCreatureCard("a creature card")));
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
    }

    private HarshScrutiny(final HarshScrutiny card) {
        super(card);
    }

    @Override
    public HarshScrutiny copy() {
        return new HarshScrutiny(this);
    }
}
