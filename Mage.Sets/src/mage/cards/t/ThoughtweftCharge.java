package mage.cards.t;

import mage.abilities.condition.common.CreatureEnteredUnderYourControlCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CreatureEnteredControllerWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoughtweftCharge extends CardImpl {

    public ThoughtweftCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +3/+3 until end of turn. If a creature entered the battlefield under your control this turn, draw a card.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), CreatureEnteredUnderYourControlCondition.instance,
                "If a creature entered the battlefield under your control this turn, draw a card"
        ));
        this.getSpellAbility().addWatcher(new CreatureEnteredControllerWatcher());
    }

    private ThoughtweftCharge(final ThoughtweftCharge card) {
        super(card);
    }

    @Override
    public ThoughtweftCharge copy() {
        return new ThoughtweftCharge(this);
    }
}
