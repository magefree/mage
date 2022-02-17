package mage.cards.e;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class EpicConfrontation extends CardImpl {

    public EpicConfrontation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature you control gets +1/+2 until end of turn. It fights target creature you don't control.
        Effect effect = new BoostTargetEffect(1, 2, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        effect = new FightTargetsEffect();
        effect.setText("It fights target creature you don't control. " +
                "<i>(Each deals damage equal to its power to the other.)</i>");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL);
        this.getSpellAbility().addTarget(target);
    }

    private EpicConfrontation(final EpicConfrontation card) {
        super(card);
    }

    @Override
    public EpicConfrontation copy() {
        return new EpicConfrontation(this);
    }
}
