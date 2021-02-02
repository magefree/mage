

package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */


public final class Mindstatic extends CardImpl {

    public Mindstatic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Counter target spell unless it's controller pays {6}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(6)));

    }

    private Mindstatic(final Mindstatic card) {
        super(card);
    }

    @Override
    public Mindstatic copy() {
        return new Mindstatic(this);
    }

}
