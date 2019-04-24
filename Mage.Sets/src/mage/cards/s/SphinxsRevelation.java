

package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class SphinxsRevelation extends CardImpl {

    public SphinxsRevelation (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{W}{U}{U}");


        // You gain X life and draw X cards.
        ManacostVariableValue manaX = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new GainLifeEffect(manaX));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(manaX));
    }

    public SphinxsRevelation (final SphinxsRevelation card) {
        super(card);
    }

    @Override
    public SphinxsRevelation copy() {
        return new SphinxsRevelation(this);
    }
}