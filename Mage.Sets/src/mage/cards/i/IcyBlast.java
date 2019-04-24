
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class IcyBlast extends CardImpl {

    public IcyBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Tap X target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect("X target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false));

        // <i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, those creatures don't untap during their controllers' next untap steps.
        Effect effect = new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersNextUntapStepTargetEffect(),
                new LockedInCondition(FerociousCondition.instance));
        effect.setText("<br/><i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, those creatures don't untap during their controllers' next untap steps");
        this.getSpellAbility().addEffect(effect);
    }

    public IcyBlast(final IcyBlast card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numberToTap = ability.getManaCostsToPay().getX();
            numberToTap = Math.min(game.getBattlefield().count(StaticFilters.FILTER_PERMANENT_CREATURE, ability.getSourceId(), ability.getControllerId(), game), numberToTap);
            ability.addTarget(new TargetCreaturePermanent(numberToTap));
        }
    }

    @Override
    public IcyBlast copy() {
        return new IcyBlast(this);
    }
}
