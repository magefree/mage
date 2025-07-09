package mage.cards.d;

import mage.abilities.condition.common.VoidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DecodeTransmissions extends CardImpl {

    public DecodeTransmissions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // You draw two cards and lose 2 life.
        // Void -- If a nonland permanent left the battlefield this turn or a spell was warped this turn, instead you draw two cards and each opponent loses 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2, true));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LoseLifeOpponentsEffect(2), new LoseLifeSourceControllerEffect(2),
                VoidCondition.instance, "and lose 2 life.<br>" + AbilityWord.VOID.formatWord() +
                "If a nonland permanent left the battlefield this turn or a spell was warped this turn, " +
                "instead you draw two cards and each opponent loses 2 life"
        ));
        this.getSpellAbility().addHint(VoidCondition.getHint());
        this.getSpellAbility().addWatcher(new VoidWatcher());
    }

    private DecodeTransmissions(final DecodeTransmissions card) {
        super(card);
    }

    @Override
    public DecodeTransmissions copy() {
        return new DecodeTransmissions(this);
    }
}
