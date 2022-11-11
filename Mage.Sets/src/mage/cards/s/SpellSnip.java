
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class SpellSnip extends CardImpl {

    public SpellSnip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Counter target spell unless its controller pays {1}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(1)));
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SpellSnip(final SpellSnip card) {
        super(card);
    }

    @Override
    public SpellSnip copy() {
        return new SpellSnip(this);
    }
}
