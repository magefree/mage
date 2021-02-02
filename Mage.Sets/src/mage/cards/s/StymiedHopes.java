
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
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
public final class StymiedHopes extends CardImpl {

    public StymiedHopes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Counter target spell unless its controller pays {1}. Scry 1.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(1)));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ScryEffect(1));

    }

    private StymiedHopes(final StymiedHopes card) {
        super(card);
    }

    @Override
    public StymiedHopes copy() {
        return new StymiedHopes(this);
    }
}
