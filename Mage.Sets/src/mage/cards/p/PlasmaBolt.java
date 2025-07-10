package mage.cards.p;

import mage.abilities.condition.common.VoidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlasmaBolt extends CardImpl {

    public PlasmaBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Plasma Bolt deals 2 damage to any target.
        // Void -- Plasma Bolt deals 3 damage instead if a nonland permanent left the battlefield this turn or a spell was warped this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(3), new DamageTargetEffect(2), VoidCondition.instance,
                "{this} deals 2 damage to any target.<br>" + AbilityWord.VOID.formatWord() +
                        "{this} deals 3 damage instead if " + VoidCondition.instance
        ));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addHint(VoidCondition.getHint());
        this.getSpellAbility().addWatcher(new VoidWatcher());
    }

    private PlasmaBolt(final PlasmaBolt card) {
        super(card);
    }

    @Override
    public PlasmaBolt copy() {
        return new PlasmaBolt(this);
    }
}
