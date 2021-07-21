package mage.cards.g;

import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuidingVoice extends CardImpl {

    public GuidingVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Put a +1/+1 counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Learn (You may reveal a Lesson card you own from outside the game and put it into your hand, or discard a card to draw a card.)
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private GuidingVoice(final GuidingVoice card) {
        super(card);
    }

    @Override
    public GuidingVoice copy() {
        return new GuidingVoice(this);
    }
}
