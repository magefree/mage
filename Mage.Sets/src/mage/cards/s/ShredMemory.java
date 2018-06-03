
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author LevelX2
 */
public final class ShredMemory extends CardImpl {

    public ShredMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Exile up to four target cards from a single graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 4, new FilterCard("cards")));
        // Transmute {1}{B}{B}
        this.addAbility(new TransmuteAbility("{1}{B}{B}"));
    }

    public ShredMemory(final ShredMemory card) {
        super(card);
    }

    @Override
    public ShredMemory copy() {
        return new ShredMemory(this);
    }
}
