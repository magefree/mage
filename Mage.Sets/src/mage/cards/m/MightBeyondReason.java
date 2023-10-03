package mage.cards.m;

import java.util.UUID;

import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public final class MightBeyondReason extends CardImpl {

    public MightBeyondReason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Put two +1/+1 counter on target creature.
        // <i>Delirium</i> &mdash; Put three +1/+1 counter on that creature instead if there are four or more card types among cards in your graveyard.
        getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)),
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
                DeliriumCondition.instance,
                "Put two +1/+1 counters on target creature.<br>"
                        + "<i>Delirium</i> &mdash; Put three +1/+1 counters on that creature instead if there are four or more card types among cards in your graveyard"
        ));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private MightBeyondReason(final MightBeyondReason card) {
        super(card);
    }

    @Override
    public MightBeyondReason copy() {
        return new MightBeyondReason(this);
    }
}
