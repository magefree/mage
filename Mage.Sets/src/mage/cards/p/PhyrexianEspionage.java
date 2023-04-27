package mage.cards.p;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianEspionage extends CardImpl {

    public PhyrexianEspionage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Kicker {1}{B}
        this.addAbility(new KickerAbility("{1}{B}"));

        // Draw two cards. If this spell was kicked, each opponent discards a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardEachPlayerEffect(TargetController.OPPONENT), KickedCondition.ONCE,
                "If this spell was kicked, each opponent discards a card"
        ));
    }

    private PhyrexianEspionage(final PhyrexianEspionage card) {
        super(card);
    }

    @Override
    public PhyrexianEspionage copy() {
        return new PhyrexianEspionage(this);
    }
}
