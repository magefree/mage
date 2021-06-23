
package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class Prosperity extends CardImpl {

    public Prosperity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}");


        // Each player draws X cards.
        this.getSpellAbility().addEffect(new DrawCardAllEffect(ManacostVariableValue.REGULAR));
    }

    private Prosperity(final Prosperity card) {
        super(card);
    }

    @Override
    public Prosperity copy() {
        return new Prosperity(this);
    }
}
