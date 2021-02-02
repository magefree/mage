
package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public final class HinderingTouch extends CardImpl {

    public HinderingTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Counter target spell unless its controller pays {2}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        // Storm
        this.addAbility(new StormAbility());
    }

    private HinderingTouch(final HinderingTouch card) {
        super(card);
    }

    @Override
    public HinderingTouch copy() {
        return new HinderingTouch(this);
    }
}
