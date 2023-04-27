package mage.cards.f;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class FiresOfVictory extends CardImpl {

    public FiresOfVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Kicker {2}{U}
        this.addAbility(new KickerAbility("{2}{U}"));

        // If this spell was kicked, draw a card. Fires of Victory deals damage to target creature or planeswalker equal to the number of cards in your hand.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                KickedCondition.ONCE,
                "If this spell was kicked, draw a card."
        ));
        this.getSpellAbility().addEffect(new DamageTargetEffect(CardsInControllerHandCount.instance)
                .setText("{this} deals damage to target creature or planeswalker equal to the number of cards in your hand."));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private FiresOfVictory(final FiresOfVictory card) {
        super(card);
    }

    @Override
    public FiresOfVictory copy() {
        return new FiresOfVictory(this);
    }
}
