package mage.cards.w;

import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterbendingLesson extends CardImpl {

    public WaterbendingLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        this.subtype.add(SubType.LESSON);

        // Draw three cards. Then discard a card unless you waterbend {2}.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(1),
                new WaterbendCost(2).setText("waterbend {2} instead of discarding a card")
        ).setText("Then discard a card unless you waterbend {2}"));
    }

    private WaterbendingLesson(final WaterbendingLesson card) {
        super(card);
    }

    @Override
    public WaterbendingLesson copy() {
        return new WaterbendingLesson(this);
    }
}
