package mage.cards.t;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TranscendentMessage extends CardImpl {

    public TranscendentMessage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}{U}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Draw X cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
    }

    private TranscendentMessage(final TranscendentMessage card) {
        super(card);
    }

    @Override
    public TranscendentMessage copy() {
        return new TranscendentMessage(this);
    }
}
