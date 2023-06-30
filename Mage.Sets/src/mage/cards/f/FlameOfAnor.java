package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlameOfAnor extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.WIZARD));
    private static final Hint hint = new ConditionHint(condition, "You control a Wizard");

    public FlameOfAnor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Choose one. If you control a Wizard as you cast this spell, you may choose two instead.
        this.getSpellAbility().getModes().setMoreCondition(condition);
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a Wizard as you cast this spell, you may choose two instead."
        );
        this.getSpellAbility().addHint(hint);

        // * Target player draws two cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Destroy target artifact.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetArtifactPermanent()));

        // * Flame of Anor deals 5 damage to target creature.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(5)).addTarget(new TargetCreaturePermanent()));
    }

    private FlameOfAnor(final FlameOfAnor card) {
        super(card);
    }

    @Override
    public FlameOfAnor copy() {
        return new FlameOfAnor(this);
    }
}
