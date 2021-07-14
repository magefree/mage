

package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class MindSpring extends CardImpl {

    public MindSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{U}");

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
    }

    private MindSpring(final MindSpring card) {
        super(card);
    }

    @Override
    public MindSpring copy() {
        return new MindSpring(this);
    }

}
