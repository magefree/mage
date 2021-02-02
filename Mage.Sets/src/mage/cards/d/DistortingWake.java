
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DistortingWake extends CardImpl {

    public DistortingWake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}{U}");

        // Return X target nonland permanents to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target nonland permanents to their owners' hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(DistortingWakeAdjuster.instance);
    }

    private DistortingWake(final DistortingWake card) {
        super(card);
    }

    @Override
    public DistortingWake copy() {
        return new DistortingWake(this);
    }
}

enum DistortingWakeAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        Target target = new TargetNonlandPermanent(xValue, xValue,
                new FilterNonlandPermanent(xValue + " target nonland permanent(s)"), false);
        ability.getTargets().clear();
        ability.getTargets().add(target);
    }
}