package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MeteorBlast extends CardImpl {

    public MeteorBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");

        // Meteor Blast deals 4 damage to each of X target creatures and/or players.
        this.getSpellAbility().addEffect(
                new DamageTargetEffect(4).setText("{this} deals 4 damage to each of X targets")
        );
        this.getSpellAbility().setTargetAdjuster(MeteorBlastAdjuster.instance);
    }

    private MeteorBlast(final MeteorBlast card) {
        super(card);
    }

    @Override
    public MeteorBlast copy() {
        return new MeteorBlast(this);
    }
}

enum MeteorBlastAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            ability.addTarget(new TargetAnyTarget(xValue));
        }
    }
}
