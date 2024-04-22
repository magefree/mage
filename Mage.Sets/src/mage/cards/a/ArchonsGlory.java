package mage.cards.a;

import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BargainAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchonsGlory extends CardImpl {

    public ArchonsGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Bargain
        this.addAbility(new BargainAbility());

        // Target creature gets +2/+2 until end of turn. If this spell was bargained, that creature also gains flying and lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(
                        new GainAbilityTargetEffect(FlyingAbility.getInstance()),
                        new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                ), BargainedCondition.instance, "if this spell was bargained, " +
                "that creature also gains flying and lifelink until end of turn"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ArchonsGlory(final ArchonsGlory card) {
        super(card);
    }

    @Override
    public ArchonsGlory copy() {
        return new ArchonsGlory(this);
    }
}
