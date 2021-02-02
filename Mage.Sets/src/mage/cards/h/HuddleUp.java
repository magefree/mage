
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.AssistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class HuddleUp extends CardImpl {

    public HuddleUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Assist
        this.addAbility(new AssistAbility());

        // Two target players each draw a card.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(1).setText("Two target players each draw a card"));
        this.getSpellAbility().addTarget(new TargetPlayer(2));
    }

    private HuddleUp(final HuddleUp card) {
        super(card);
    }

    @Override
    public HuddleUp copy() {
        return new HuddleUp(this);
    }
}
