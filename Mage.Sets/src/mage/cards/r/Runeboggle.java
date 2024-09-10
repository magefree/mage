
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author Loki
 */
public final class Runeboggle extends CardImpl {

    public Runeboggle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(1)));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Runeboggle(final Runeboggle card) {
        super(card);
    }

    @Override
    public Runeboggle copy() {
        return new Runeboggle(this);
    }
}
