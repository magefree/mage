
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Condescend extends CardImpl {

    public Condescend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}");


        // Counter target spell unless its controller pays {X}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetSpell());
        // Scry 2.
        this.getSpellAbility().addEffect(new ScryEffect(2));

    }

    private Condescend(final Condescend card) {
        super(card);
    }

    @Override
    public Condescend copy() {
        return new Condescend(this);
    }
}
