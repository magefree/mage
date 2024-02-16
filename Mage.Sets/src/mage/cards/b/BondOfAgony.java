
package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author magenoxx
 */
public final class BondOfAgony extends CardImpl {

    public BondOfAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}");

        DynamicValue xValue = ManacostVariableValue.REGULAR;

        // As an additional cost to cast Bond of Agony, pay X life.
        // magenoxx: here we don't use PayVariableLifeCost as {X} shouldn't actually be announced
        this.getSpellAbility().addCost(new PayLifeCost(xValue, "X life"));

        // Each other player loses X life.
        this.getSpellAbility().addEffect(new LoseLifeOpponentsEffect(xValue).setText("each other player loses X life"));
    }

    private BondOfAgony(final BondOfAgony card) {
        super(card);
    }

    @Override
    public BondOfAgony copy() {
        return new BondOfAgony(this);
    }
}
