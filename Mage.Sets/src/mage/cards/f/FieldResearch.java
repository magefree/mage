package mage.cards.f;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FieldResearch extends CardImpl {

    public FieldResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Kicker {2}{U}
        this.addAbility(new KickerAbility("{2}{U}"));

        // Draw two cards. If this spell was kicked, draw three cards instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(3), new DrawCardSourceControllerEffect(2),
                KickedCondition.instance, "Draw two cards. If this spell was kicked, draw three cards instead."
        ));
    }

    private FieldResearch(final FieldResearch card) {
        super(card);
    }

    @Override
    public FieldResearch copy() {
        return new FieldResearch(this);
    }
}
