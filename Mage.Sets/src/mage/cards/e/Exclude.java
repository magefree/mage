
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureSpell;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Exclude extends CardImpl {

    public Exclude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Counter target creature spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(new FilterCreatureSpell()));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Exclude(final Exclude card) {
        super(card);
    }

    @Override
    public Exclude copy() {
        return new Exclude(this);
    }
}
