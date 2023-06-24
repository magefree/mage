package mage.cards.v;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VivisurgeonsInsight extends CardImpl {

    public VivisurgeonsInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Draw three cards. Proliferate.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private VivisurgeonsInsight(final VivisurgeonsInsight card) {
        super(card);
    }

    @Override
    public VivisurgeonsInsight copy() {
        return new VivisurgeonsInsight(this);
    }
}
