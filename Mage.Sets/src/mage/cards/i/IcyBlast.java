package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class IcyBlast extends CardImpl {

    public IcyBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Tap X target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap X target creatures"));

        // <i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, those creatures don't untap during their controllers' next untap steps.
        Effect effect = new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersNextUntapStepTargetEffect(),
                new LockedInCondition(FerociousCondition.instance));
        effect.setText("<br/><i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, those creatures don't untap during their controllers' next untap steps");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(IcyBlastAdjuster.instance);
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private IcyBlast(final IcyBlast card) {
        super(card);
    }

    @Override
    public IcyBlast copy() {
        return new IcyBlast(this);
    }
}

enum IcyBlastAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}