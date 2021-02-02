
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class ScatterToTheWinds extends CardImpl {

    public ScatterToTheWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Awaken 3-{4}{U}{U}
        this.addAbility(new AwakenAbility(this, 3, "{4}{U}{U}"));
    }

    private ScatterToTheWinds(final ScatterToTheWinds card) {
        super(card);
    }

    @Override
    public ScatterToTheWinds copy() {
        return new ScatterToTheWinds(this);
    }
}
