package mage.cards.t;

import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TogetherAsOne extends CardImpl {

    public TogetherAsOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}");

        // Converge -- Target player draws X cards, Together as One deals X damage to any target, and you gain X life, where X is the number of colors of mana spent to cast this spell.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(ColorsOfManaSpentToCastCount.getInstance())
                .setText("target player draws X cards"));
        this.getSpellAbility().addTarget(new TargetPlayer().withChooseHint("draw cards"));
        this.getSpellAbility().addEffect(new DamageTargetEffect(ColorsOfManaSpentToCastCount.getInstance())
                .setTargetPointer(new SecondTargetPointer()).setText(", {this} deals X damage to any target"));
        this.getSpellAbility().addTarget(new TargetAnyTarget().withChooseHint("deal damage"));
        this.getSpellAbility().addEffect(new GainLifeEffect(ColorsOfManaSpentToCastCount.getInstance())
                .concatBy(", and"));
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
    }

    private TogetherAsOne(final TogetherAsOne card) {
        super(card);
    }

    @Override
    public TogetherAsOne copy() {
        return new TogetherAsOne(this);
    }
}
