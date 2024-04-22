
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class BrightReprisal extends CardImpl {

    public BrightReprisal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Destroy target attacking creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private BrightReprisal(final BrightReprisal card) {
        super(card);
    }

    @Override
    public BrightReprisal copy() {
        return new BrightReprisal(this);
    }
}
