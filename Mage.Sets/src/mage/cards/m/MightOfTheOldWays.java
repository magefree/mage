package mage.cards.m;

import mage.abilities.condition.common.CovenCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MightOfTheOldWays extends CardImpl {

    public MightOfTheOldWays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));

        // Coven — Then if you control three or more creatures with different powers, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), CovenCondition.instance,
                "<br>" + AbilityWord.COVEN.formatWord() + "Then if you control " +
                        "three or more creatures with different powers, draw a card"
        ));
        this.getSpellAbility().addHint(CovenHint.instance);
    }

    private MightOfTheOldWays(final MightOfTheOldWays card) {
        super(card);
    }

    @Override
    public MightOfTheOldWays copy() {
        return new MightOfTheOldWays(this);
    }
}
