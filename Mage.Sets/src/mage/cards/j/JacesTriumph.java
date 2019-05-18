package mage.cards.j;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JacesTriumph extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent(SubType.JACE);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public JacesTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Draw two cards. If you control a Jace planeswalker, draw three cards instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(3), new DrawCardSourceControllerEffect(2),
                condition, "Draw two cards. If you control a Jace planeswalker, draw three cards instead."
        ));
    }

    private JacesTriumph(final JacesTriumph card) {
        super(card);
    }

    @Override
    public JacesTriumph copy() {
        return new JacesTriumph(this);
    }
}
