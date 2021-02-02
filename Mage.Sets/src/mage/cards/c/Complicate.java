
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
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
 * @author emerald000
 */
public final class Complicate extends CardImpl {

    public Complicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target spell unless its controller pays {3}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        this.getSpellAbility().addTarget(new TargetSpell());

        // Cycling {2}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{U}")));

        // When you cycle Complicate, you may counter target spell unless its controller pays {1}.
        Ability ability = new CycleTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(1)), true);
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private Complicate(final Complicate card) {
        super(card);
    }

    @Override
    public Complicate copy() {
        return new Complicate(this);
    }
}
