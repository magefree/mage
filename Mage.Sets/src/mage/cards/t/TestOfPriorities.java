package mage.cards.t;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class TestOfPriorities extends CardImpl {

    public TestOfPriorities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose one --
        // * Power to Engines -- Surveil 2, then draw a card.
        this.getSpellAbility().addEffect(new SurveilEffect(2));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.getSpellAbility().withFirstModeFlavorWord("Power to Engines");

        // * Power to Life Support -- Exile target creature you control. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Mode mode = new Mode(new ExileReturnBattlefieldNextEndStepTargetEffect());
        mode.addTarget(new TargetControlledCreaturePermanent());
        mode.withFlavorWord("Power to Life Support");
        this.getSpellAbility().addMode(mode);
    }

    private TestOfPriorities(final TestOfPriorities card) {
        super(card);
    }

    @Override
    public TestOfPriorities copy() {
        return new TestOfPriorities(this);
    }
}
