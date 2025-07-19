package mage.cards.h;

import mage.abilities.condition.common.VoidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HymnOfTheFaller extends CardImpl {

    public HymnOfTheFaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Surveil 1, then you draw a card and lose 1 life.
        this.getSpellAbility().addEffect(new SurveilEffect(1, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, true).concatBy(", then"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));

        // Void -- If a nonland permanent left the battlefield this turn or a spell was warped this turn, draw another card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), VoidCondition.instance,
                "<br>" + AbilityWord.VOID.formatWord() + "If a nonland permanent left the battlefield " +
                        "this turn or a spell was warped this turn, draw another card"
        ));
        this.getSpellAbility().addHint(VoidCondition.getHint());
        this.getSpellAbility().addWatcher(new VoidWatcher());
    }

    private HymnOfTheFaller(final HymnOfTheFaller card) {
        super(card);
    }

    @Override
    public HymnOfTheFaller copy() {
        return new HymnOfTheFaller(this);
    }
}
