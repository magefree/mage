package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeliodsIntervention extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.REGULAR, 2);

    public HeliodsIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");

        // Choose one —
        // • Destroy X target artifacts and/or enchantments.
        this.getSpellAbility().addEffect(new DestroyTargetEffect()
                .setText("Destroy X target artifacts and/or enchantments."));
        this.getSpellAbility().setTargetAdjuster(HeliodsInterventionAdjuster.instance);

        // • Target player gains twice X life.
        Mode mode = new Mode(new GainLifeTargetEffect(xValue).setText("Target player gains twice X life"));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private HeliodsIntervention(final HeliodsIntervention card) {
        super(card);
    }

    @Override
    public HeliodsIntervention copy() {
        return new HeliodsIntervention(this);
    }
}

enum HeliodsInterventionAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Mode mode = ability.getModes().getMode();
        if (mode.getEffects().stream().noneMatch(DestroyTargetEffect.class::isInstance)) {
            return;
        }
        mode.getTargets().clear();
        mode.addTarget(new TargetPermanent(
                ability.getManaCostsToPay().getX(), StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT
        ));
    }
}